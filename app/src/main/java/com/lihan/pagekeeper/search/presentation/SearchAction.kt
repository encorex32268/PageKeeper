package com.lihan.pagekeeper.search.presentation

sealed interface SearchAction {
    data object OnBackClick: SearchAction
    data object OnCloseClick: SearchAction
    data object OnDoneClick: SearchAction
    data class OnItemClick(val id: Int): SearchAction
}