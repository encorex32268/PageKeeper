package com.lihan.pagekeeper.library.presentation

sealed interface LibraryAction {
    data object DismissUnsupportedFileDialog: LibraryAction
    data object DismissDeleteDialog: LibraryAction
    data object MenuClick: LibraryAction
    data object SearchClick: LibraryAction
    data class ItemFinishedClick(val id: Int): LibraryAction
    data class ItemFavoriteClick(val id: Int): LibraryAction
    data class ItemShareClick(val id: Int): LibraryAction
    data class ItemDeleteClick(val id: Int): LibraryAction
    data class ItemSelectClick(val id: Int,val isSelected: Boolean): LibraryAction
    data object SelectModeChanged: LibraryAction
    data object ImportBookClick: LibraryAction
    data object CleanText: LibraryAction
    data object StartSearch: LibraryAction
}