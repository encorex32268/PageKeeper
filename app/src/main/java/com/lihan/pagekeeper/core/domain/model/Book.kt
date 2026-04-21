package com.lihan.pagekeeper.core.domain.model

data class Book(
    val id: Int?,
    val title: String,
    val author: String,
    val uriString: String,
    val isFavorite: Boolean
)
