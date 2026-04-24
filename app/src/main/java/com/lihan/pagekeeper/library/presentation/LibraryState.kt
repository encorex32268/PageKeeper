package com.lihan.pagekeeper.library.presentation

import androidx.compose.foundation.text.input.TextFieldState
import com.lihan.pagekeeper.library.presentation.model.BookUi
import com.lihan.pagekeeper.search.presentation.model.SearchBookUi

data class LibraryState(
    val items: List<BookUi> = emptyList(),
    val isLoading: Boolean = false,
    val isSelectMode: Boolean = false,
    val isShowUnsupportedDialog: Boolean = false,
    val isShowDeleteDialog: Boolean = false,
    val searchTextField: TextFieldState = TextFieldState(),
    val isSearching: Boolean = false,
    val searchedItems: List<BookUi> = emptyList(),
){

    val selectedBookUis: List<BookUi>
        get() = items.filter { it.isSelected }

    val favoriteBookUis: List<BookUi>
        get() = items.filter { it.isFavorite }

    val finishedBookUis: List<BookUi>
        get() = items.filter { it.isFinished }
}
