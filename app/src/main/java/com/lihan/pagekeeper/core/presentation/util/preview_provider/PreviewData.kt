package com.lihan.pagekeeper.core.presentation.util.preview_provider

import com.lihan.pagekeeper.library.presentation.model.BookUi
import com.lihan.pagekeeper.search.presentation.model.SearchBookUi

object PreviewData {
    val bookUis = (0..10).map {
        BookUi(
            id = it,
            title = "Book Title - $it",
            author = "Author - $it",
            imageUrl = null,
            isFavorite = it % 2 == 0,
            isFinished = it % 3 == 0,
            isSelected = false
        )
    }
    val searchBookUis = (0..10).map {
        SearchBookUi(
            id = it,
            title = "SearchBook Title - $it",
            author = "Author-$it",
            imageUrl = null
        )
    }
}