package com.lihan.pagekeeper.core.presentation.mapper

import com.lihan.pagekeeper.core.domain.model.Book
import com.lihan.pagekeeper.library.presentation.model.BookUi
import kotlin.String

fun Book.toUi(): BookUi? {
    if (id == null) return null
    return BookUi(
        id = id,
        title = title,
        author = author,
        fileUriPath = fileUriPath,
        imageFilePath = imageFilePath,
        isFavorite = isFavorite,
        isSelected = false,
        isFinished = isReadFinished,
    )
}