package com.lihan.pagekeeper.core.data.mapper

import com.lihan.pagekeeper.core.data.local.BookEntity
import com.lihan.pagekeeper.core.domain.model.Book

fun BookEntity.toDomain(): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        fileUriPath = fileUriPath,
        imageFilePath = imageFilePath,
        isFavorite = isFavorite,
        isReadFinished = isReadFinished
    )
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        author = author,
        fileUriPath = fileUriPath,
        imageFilePath = imageFilePath,
        isFavorite = isFavorite,
        isReadFinished = isReadFinished
    )
}