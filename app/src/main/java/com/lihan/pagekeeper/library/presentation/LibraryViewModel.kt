@file:OptIn(FlowPreview::class)

package com.lihan.pagekeeper.library.presentation

import android.net.Uri
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.pagekeeper.core.domain.BookRepository
import com.lihan.pagekeeper.core.domain.FileManager
import com.lihan.pagekeeper.core.domain.model.Book
import com.lihan.pagekeeper.core.presentation.mapper.toUi
import com.lihan.pagekeeper.core.presentation.util.BitmapConverter
import com.lihan.pagekeeper.core.presentation.util.FB2FileParser
import com.lihan.pagekeeper.library.presentation.model.BookUi
import com.lihan.pagekeeper.search.presentation.mapper.toSearchBookUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val bookRepository: BookRepository,
    private val fb2FileParser: FB2FileParser,
    private val fileManager: FileManager
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LibraryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeBooks()
                observeSearchField()
                hasLoadedInitialData = true
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            LibraryState()
        )


    fun onAction(action: LibraryAction) {
        when (action) {
            LibraryAction.DismissDeleteDialog -> {
                _state.update {
                    it.copy(
                        isShowDeleteDialog = false,
                        selectedBook = null
                    )
                }
            }

            LibraryAction.DismissUnsupportedFileDialog -> {
                _state.update {
                    it.copy(
                        isShowUnsupportedDialog = false
                    )
                }
            }

            is LibraryAction.ItemDeleteClick -> {
                if (state.value.isSelectMode) return
                val wantDeleteBookUi = state.value.items.find { it.id == action.id }
                if (wantDeleteBookUi == null) return

                _state.update {
                    it.copy(
                        isShowDeleteDialog = true,
                        selectedBook = wantDeleteBookUi
                    )
                }
            }

            is LibraryAction.ItemFavoriteClick -> {
                if (state.value.isSelectMode) return
                viewModelScope.launch {
                    bookRepository.updateFavoriteStatus(action.id,!action.isFavorite)
                }
            }

            is LibraryAction.ItemFinishedClick -> {
                if (state.value.isSelectMode) return
                viewModelScope.launch {
                    bookRepository.updateFinishedStatus(action.id,!action.isFinished)
                }
            }

            is LibraryAction.ItemShareClick -> Unit
            is LibraryAction.ItemSelectClick -> {
                _state.update {
                    it.copy(
                        items = it.items.map { bookUi ->
                            if (bookUi.id == action.id) {
                                bookUi.copy(isSelected = !action.isSelected)
                            } else {
                                bookUi
                            }
                        }
                    )
                }
            }

            LibraryAction.MenuClick -> Unit
            LibraryAction.SearchClick -> Unit
            LibraryAction.SelectModeChanged -> {
                _state.update {
                    it.copy(
                        isSelectMode = !it.isSelectMode
                    )
                }
            }

            is LibraryAction.ImportBookClick -> Unit
            LibraryAction.ClearText -> {
                state.value.searchTextField.clearText()
                _state.update {
                    it.copy(
                        isSearching = false,
                        searchedItems = emptyList()
                    )
                }
            }

            LibraryAction.StartSearch -> {
                _state.update {
                    it.copy(
                        isSearching = true
                    )
                }
            }

            is LibraryAction.UpsertBook -> upsertBook(action.uri)
            LibraryAction.DeleteDialogConfirm -> {
                val currentState = state.value
                val booksToBeDeleted = mutableListOf<BookUi>()
                
                viewModelScope.launch {
                    if (currentState.selectedBook != null) {
                        booksToBeDeleted.add(currentState.selectedBook)
                    } else {
                        booksToBeDeleted.addAll(currentState.selectedBookUis)
                    }

                    if (booksToBeDeleted.isEmpty()) return@launch


                    val pathsToDelete = mutableListOf<String>()
                    booksToBeDeleted.forEach { book ->
                        pathsToDelete.add(book.fileUriPath)
                        pathsToDelete.add(book.imageFilePath)
                    }

                    bookRepository.deleteBooksByIds(booksToBeDeleted.map { it.id })
                    fileManager.removeFiles(pathsToDelete)

                    _state.update {
                        it.copy(
                            isShowDeleteDialog = false,
                            selectedBook = null,
                            isSelectMode = false
                        )
                    }
                }
            }

            is LibraryAction.ItemLongClick -> {
                _state.update {
                    it.copy(
                        isSelectMode = true,
                        items = state.value.items.map { bookUi ->
                            if (bookUi.id == action.id) {
                                bookUi.copy(isSelected = true)
                            } else {
                                bookUi
                            }
                        }
                    )
                }
            }

            LibraryAction.SelectMode.DeleteClick -> {
                if (state.value.selectedBookUis.isEmpty()) return
                _state.update {
                    it.copy(
                        isShowDeleteDialog = true
                    )
                }
            }

            LibraryAction.SelectMode.BackClick -> {
                _state.update {
                    it.copy(
                        items = it.items.map { bookUi ->
                            bookUi.copy(isSelected = false)
                        },
                        isSelectMode = false
                    )
                }
            }

            LibraryAction.SelectMode.FavoriteClick -> Unit
            LibraryAction.SelectMode.ShareClick -> Unit

        }
    }

    private fun upsertBook(uri: Uri) {


        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            val fB2Metadata = fb2FileParser.parse(uri)

            if (fB2Metadata == null){
                _state.update { it.copy(
                    isShowUnsupportedDialog = true,
                    isLoading = false
                ) }
                return@launch
            }
            val coverByteArray = BitmapConverter.toByteArray(fB2Metadata.cover)
            val imageFilePath = fileManager.saveBitmapToDevice(coverByteArray)
            val internalFileUriPath = fileManager.saveBook(uri)


            bookRepository.upsert(
                book = Book(
                    id = null,
                    title = fB2Metadata.title ?: "",
                    author = fB2Metadata.author ?: "",
                    imageFilePath =imageFilePath,
                    fileUriPath = internalFileUriPath,
                    isFavorite = false,
                    isReadFinished = false
                )
            )

            delay(500)
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }


    private fun observeBooks() {
        bookRepository
            .getBooks()
            .onEach { books ->
                val bookUis = books
                    .mapNotNull { it.toUi() }

                _state.update {
                    it.copy(
                        items = bookUis
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchField() {

        snapshotFlow { state.value.searchTextField.text.toString() }
            .distinctUntilChanged()
            .debounce(500)
            .onEach { searchText ->
                if (searchText.isNotEmpty()) {
                    val searchBookUis = bookRepository.searchBooks(
                        text = searchText
                    ).first().mapNotNull { book ->
                        book.toUi()
                    }


                    _state.update {
                        it.copy(
                            searchedItems = searchBookUis
                        )
                    }
                }

            }.launchIn(viewModelScope)
    }

}