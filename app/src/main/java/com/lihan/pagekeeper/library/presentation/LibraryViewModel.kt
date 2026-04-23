package com.lihan.pagekeeper.library.presentation

import android.net.Uri
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.pagekeeper.core.domain.BookRepository
import com.lihan.pagekeeper.core.domain.FileManager
import com.lihan.pagekeeper.core.domain.model.Book
import com.lihan.pagekeeper.core.presentation.mapper.toUi
import com.lihan.pagekeeper.core.presentation.util.BitmapConverter
import com.lihan.pagekeeper.core.presentation.util.EpubMetadataParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val bookRepository: BookRepository,
    private val epubMetadataParser: EpubMetadataParser,
    private val fileManager: FileManager
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LibraryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData){
                observeBooks()
                hasLoadedInitialData = true
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            LibraryState()
        )


    fun onAction(action: LibraryAction){
        when(action){
            LibraryAction.DismissDeleteDialog -> {
                _state.update { it.copy(
                    isShowDeleteDialog = false
                ) }
            }
            LibraryAction.DismissUnsupportedFileDialog -> {
                _state.update { it.copy(
                    isShowUnsupportedDialog = false
                ) }
            }
            is LibraryAction.ItemDeleteClick -> {
                val wantDeleteBookUi = state.value.items.find { it.id == action.id }
                if (wantDeleteBookUi == null) return
                _state.update { it.copy(
                    isShowDeleteDialog = true,
                    selectedBookUis =  listOf(wantDeleteBookUi)
                ) }
            }
            is LibraryAction.ItemFavoriteClick -> {
                //TODO: Repository Add Favorite Id
            }
            is LibraryAction.ItemFinishedClick -> {
                //TODO: Repository Add Finished Id
            }
            is LibraryAction.ItemSelectClick -> {
                _state.update { it.copy(
                    items = emptyList()
                ) }
            }
            is LibraryAction.ItemShareClick -> {}

            LibraryAction.MenuClick -> Unit
            LibraryAction.SearchClick -> Unit
            LibraryAction.SelectModeChanged -> {
                _state.update { it.copy(
                    isSelectMode = !it.isSelectMode
                ) }
            }

            is LibraryAction.ImportBookClick -> Unit
            LibraryAction.CleanText -> {
                state.value.searchTextField.clearText()
                _state.update { it.copy(
                    isSearching = false
                ) }
            }
            LibraryAction.StartSearch -> {
                _state.update { it.copy(
                    isSearching = true
                ) }
            }

            is LibraryAction.UpsertBook -> upsertBook(action.uri)

            LibraryAction.DeleteDialogConfirm -> {
                val currentSelectedBookUis = state.value.selectedBookUis.map { it.id }
                if (currentSelectedBookUis.isEmpty()){
                    return
                }
                viewModelScope.launch {
                    bookRepository.deleteBooksByIds(currentSelectedBookUis)
                    _state.update { it.copy(
                        isShowDeleteDialog = false
                    ) }
                }
            }
        }
    }

    private fun upsertBook(uri: Uri) {
        viewModelScope.launch {
            println("UpsertBook: ${uri.path}")
            println("UpsertBook: ${uri.userInfo}")
            val epubMetadata = epubMetadataParser.parseEpubFile(uri)
            epubMetadata?.let {
                val coverByteArray = BitmapConverter.toByteArray(epubMetadata.cover)
                val imageFilePath = fileManager.saveBitmapToDevice(coverByteArray)
                bookRepository.upsert(
                    book = Book(
                        id = null,
                        title = epubMetadata.title ?: "",
                        author = epubMetadata.author?:"",
                        imageFilePath = imageFilePath,
                        fileUriPath = uri.toString(),
                        isFavorite = false,
                        isReadFinished = false
                    )
                )
            }
        }
    }


    private fun observeBooks(){
        bookRepository
            .getBooks()
            .onEach {  books ->
                val bookUis = books
                    .mapNotNull { it.toUi() }

                _state.update { it.copy(
                    items =bookUis
                ) }
            }
            .launchIn(viewModelScope)
    }

}