package com.lihan.pagekeeper.core.data.mapper

import com.lihan.pagekeeper.core.data.local.BookEntity
import com.lihan.pagekeeper.core.domain.model.Book

fun BookEntity.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        uriString = uriString,
        isFavorite = isFavorite
    )
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        author = author,
        uriString = uriString,
        isFavorite = isFavorite
    )
}