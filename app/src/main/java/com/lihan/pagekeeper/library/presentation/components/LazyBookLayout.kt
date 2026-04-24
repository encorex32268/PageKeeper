package com.lihan.pagekeeper.library.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LazyBookLayout(
    isSelectMode: Boolean,
    items: List<BookUi>,
    onCheckedChange: (Int, Boolean) -> Unit,
    onFinishClick: (Int, Boolean) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onShareClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val currentDeviceConfiguration = DeviceConfiguration.fromWindowSizeClass(
        currentWindowAdaptiveInfo().windowSizeClass
    )

    val isMobile = currentDeviceConfiguration.isMobile


    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 12.dp),
        columns = if (isMobile) GridCells.Fixed(1) else GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = items,
            key = { item -> item.id }
        ) { bookUi ->
            BookCard(
                title = bookUi.title,
                author = bookUi.author,
                imageUrl = bookUi.imageFilePath,
                isFavorite = bookUi.isFavorite,
                isFinished = bookUi.isFinished,
                isSelected = bookUi.isSelected,
                isSelectMode = isSelectMode,
                onCheckedChange = {
                    onCheckedChange(bookUi.id,bookUi.isSelected)
                },
                onFinishClick = {
                    onFinishClick(bookUi.id,bookUi.isFinished)
                },
                onFavoriteClick = {
                    onFavoriteClick(bookUi.id,bookUi.isFavorite)
                },
                onDeleteClick = {
                    onDeleteClick(bookUi.id)
                },
                onShareClick = {
                    onShareClick(bookUi.id)
                },
                onLongClick = {
                    onLongClick(bookUi.id)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LazyBookLayoutPreview() {
    PageKeeperTheme {
        LazyBookLayout(
            isSelectMode = false,
            items = listOf(),
            onDeleteClick = {},
            onLongClick = {},
            onShareClick = {},
            onFinishClick = { id , isSelected -> },
            onCheckedChange = { id , isSelected -> },
            onFavoriteClick = { id , isSelected -> },
        )
    }
}