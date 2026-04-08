package com.lihan.pagekeeper.library.presentation.tablet


sealed interface LibraryTabletAction {
    data object CleanText: LibraryTabletAction
    data object StartSearch: LibraryTabletAction
    data class ItemFinishedClick(val id: Int): LibraryTabletAction
    data class ItemFavoriteClick(val id: Int): LibraryTabletAction
    data class ItemShareClick(val id: Int): LibraryTabletAction
    data class ItemDeleteClick(val id: Int): LibraryTabletAction
    data class ItemSelectClick(val id: Int,val isSelected: Boolean): LibraryTabletAction
}