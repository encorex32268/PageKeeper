package com.lihan.pagekeeper.library.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LibraryAdaptive(
    isMobile: Boolean,
    isSelectMode: Boolean,
    items: List<BookUi>,
    onCheckedChange: (Int,Boolean) -> Unit,
    onFinishClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onShareClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when(isMobile){
        true -> {
            LazyColumn(
                modifier = modifier
            ) {
                items(
                    items = items,
                    key = { item -> item.id}
                ){ bookUi ->
                    BookCard(
                        title = bookUi.title,
                        author = bookUi.author,
                        imageUrl = bookUi.imageUrl,
                        isFavorite = bookUi.isFavorite,
                        isFinished = bookUi.isFinished,
                        isSelected = bookUi.isSelected,
                        isSelectMode = isSelectMode,
                        onCheckedChange = {
                            onCheckedChange(bookUi.id,bookUi.isSelected)
                        },
                        onFinishClick = {
                            onFinishClick(bookUi.id)
                        },
                        onFavoriteClick = {
                            onFavoriteClick(bookUi.id)
                        },
                        onDeleteClick = {
                            onDeleteClick(bookUi.id)
                        },
                        onShareClick = {
                            onShareClick(bookUi.id)
                        }
                    )
                }
            }
        }
        false -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2)
            ) {
                items(
                    items = items,
                    key = { item -> item.id}
                ){ bookUi ->
                    BookCard(
                        title = bookUi.title,
                        author = bookUi.author,
                        imageUrl = bookUi.imageUrl,
                        isFavorite = bookUi.isFavorite,
                        isFinished = bookUi.isFinished,
                        isSelected = bookUi.isSelected,
                        isSelectMode =  isSelectMode,
                        onCheckedChange = {
                            onCheckedChange(bookUi.id,bookUi.isSelected)
                        },
                        onFinishClick = {
                            onFinishClick(bookUi.id)
                        },
                        onFavoriteClick = {
                            onFavoriteClick(bookUi.id)
                        },
                        onDeleteClick = {
                            onDeleteClick(bookUi.id)
                        },
                        onShareClick = {
                            onShareClick(bookUi.id)
                        }
                    )
                }
            }
        }
    }

}
