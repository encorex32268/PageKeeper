package com.lihan.pagekeeper.library.presentation.model

data class BookUi(
    val id: Int,
    val title: String,
    val author: String,
    val fileUriPath: String,
    val imageFilePath: String,
    val isFavorite: Boolean,
    val isFinished: Boolean,
    val isSelected: Boolean
)
