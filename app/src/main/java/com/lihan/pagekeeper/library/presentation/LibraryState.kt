package com.lihan.pagekeeper.library.presentation

import com.lihan.pagekeeper.library.presentation.model.BookUi

data class LibraryState(
    val items: List<BookUi> = emptyList(),
    val isLoading: Boolean = false,
    val isSelectMode: Boolean = false,
    val isShowUnsupportedDialog: Boolean = false,
    val isShowDeleteDialog: Boolean = false,
    val selectedBookUi: BookUi?=null
)
