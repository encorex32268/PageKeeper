package com.lihan.pagekeeper.search.presentation

import androidx.compose.foundation.text.input.TextFieldState
import com.lihan.pagekeeper.search.presentation.model.SearchBookUi

data class SearchState(
    val items: List<SearchBookUi> = emptyList(),
    val searchTextFieldState: TextFieldState = TextFieldState()
)
