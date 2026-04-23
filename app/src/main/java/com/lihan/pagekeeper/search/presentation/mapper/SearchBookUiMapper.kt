package com.lihan.pagekeeper.search.presentation.mapper

import com.lihan.pagekeeper.core.domain.model.Book
import com.lihan.pagekeeper.search.presentation.model.SearchBookUi

fun Book.toSearchBookUi(): SearchBookUi? {
    if (id == null) return null
    return SearchBookUi(
        id = id,
        title = title,
        author = author,
        imageUrl = imageFilePath
    )
}