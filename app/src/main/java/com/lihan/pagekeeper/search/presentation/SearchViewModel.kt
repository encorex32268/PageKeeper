@file:OptIn(FlowPreview::class)

package com.lihan.pagekeeper.search.presentation

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.pagekeeper.core.domain.BookRepository
import com.lihan.pagekeeper.search.presentation.mapper.toSearchBookUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeSearchField()
//                observeSearchedItems()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SearchState()
        )

    fun onAction(action: SearchAction) {
        when (action) {
            SearchAction.OnCloseClick -> {
                state.value.searchTextFieldState.clearText()
            }

            else -> Unit
        }
    }

    private fun observeSearchField() {

        snapshotFlow { state.value.searchTextFieldState.text.toString() }
            .distinctUntilChanged()
            .debounce(500)
            .onEach { searchText ->
                println("SearchText: $searchText")
                if (searchText.isNotEmpty()) {
                    val searchBookUis = bookRepository.searchBooks(
                        text = searchText
                    ).first().mapNotNull { it.toSearchBookUi() }

                    println("SearchBookUis: $searchBookUis")

                    _state.update {
                        it.copy(
                            items = searchBookUis
                        )
                    }
                }

            }.launchIn(viewModelScope)

    }

    private fun observeSearchedItems() {
        //TODO: Search Data
    }
}