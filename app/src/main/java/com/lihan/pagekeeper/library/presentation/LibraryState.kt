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
    val selectedBookUi: BookUi?=null,
    val searchTextField: TextFieldState = TextFieldState(),
    val isSearching: Boolean = false,
    val searchedItems: List<SearchBookUi> = emptyList(),
)
