package com.lihan.pagekeeper.core.domain.model

data class Book(
    val id: Int?,
    val title: String,
    val author: String,
    val fileUriPath: String,
    val imageFilePath: String,
    val isFavorite: Boolean,
    val isReadFinished: Boolean
)
