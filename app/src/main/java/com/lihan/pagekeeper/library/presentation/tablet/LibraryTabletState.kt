package com.lihan.pagekeeper.library.presentation.tablet

import androidx.compose.foundation.text.input.TextFieldState
import com.lihan.pagekeeper.core.presentation.util.preview_provider.PreviewData
import com.lihan.pagekeeper.library.presentation.model.BookUi
import com.lihan.pagekeeper.search.presentation.model.SearchBookUi

data class LibraryTabletState(
    val searchTextField: TextFieldState = TextFieldState(),
    val isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val isSelectMode: Boolean = false,
    val searchedItems: List<SearchBookUi> = emptyList(),
    val items: List<BookUi> = PreviewData.bookUis
)