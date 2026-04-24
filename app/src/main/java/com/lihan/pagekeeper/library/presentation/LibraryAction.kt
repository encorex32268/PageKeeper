package com.lihan.pagekeeper.library.presentation

import android.net.Uri

sealed interface LibraryAction {
    data object DismissUnsupportedFileDialog: LibraryAction
    data object DismissDeleteDialog: LibraryAction
    data object MenuClick: LibraryAction
    data object SearchClick: LibraryAction
    data class ItemFinishedClick(val id: Int, val isFinished: Boolean): LibraryAction
    data class ItemFavoriteClick(val id: Int,val isFavorite: Boolean): LibraryAction
    data class ItemShareClick(val id: Int): LibraryAction
    data class ItemDeleteClick(val id: Int): LibraryAction
    data class ItemSelectClick(val id: Int,val isSelected: Boolean): LibraryAction
    data object SelectModeChanged: LibraryAction
    data object ImportBookClick: LibraryAction
    data object ClearText: LibraryAction
    data object StartSearch: LibraryAction
    data class UpsertBook(val uri: Uri): LibraryAction
    data object DeleteDialogConfirm: LibraryAction
    data class ItemLongClick(val id: Int): LibraryAction

    sealed interface SelectMode: LibraryAction {
        data object DeleteClick: SelectMode
        data object BackClick: SelectMode
        data object ShareClick: SelectMode
        data object FavoriteClick: SelectMode
    }

}