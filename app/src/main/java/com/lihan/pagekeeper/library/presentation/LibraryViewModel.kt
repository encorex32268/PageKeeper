package com.lihan.pagekeeper.library.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LibraryViewModel: ViewModel() {

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
                    selectedBookUi = wantDeleteBookUi
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

            LibraryAction.ImportBookClick -> Unit
        }
    }


    private fun observeBooks(){

    }

}