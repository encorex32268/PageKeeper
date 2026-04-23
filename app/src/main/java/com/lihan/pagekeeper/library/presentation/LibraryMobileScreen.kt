package com.lihan.pagekeeper.library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.core.presentation.components.BookSelectBar
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView
import com.lihan.pagekeeper.core.presentation.components.PKNormalTopBar
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LibraryMobileScreen(
    state: LibraryState,
    onAction: (LibraryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BGMain)
    ) {
        if (state.isSelectMode){

            BookSelectBar(
                selectedSize = state.selectedBookUis.size,
                onBack = {
                    onAction(LibraryAction.SelectMode.BackClick)
                },
                onDeleteClick = {
                    onAction(LibraryAction.SelectMode.DeleteClick)
                },
                onShareClick = {
                    onAction(LibraryAction.SelectMode.ShareClick)
                },
                onToggleFavorite = {
                    onAction(LibraryAction.SelectMode.FavoriteClick)
                }
            )
        }else{
            PKNormalTopBar(
                title = stringResource(R.string.library),
                onMenuClick = {
                    onAction(LibraryAction.MenuClick)
                },
                onSearchClick = {
                    onAction(LibraryAction.SearchClick)
                }
            )
        }
        if (state.items.isNotEmpty()){
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.items,
                    key = { item -> item.id }
                ) { bookUi ->
                    BookCard(
                        title = bookUi.title,
                        author = bookUi.author,
                        imageUrl = bookUi.imageFilePath,
                        isFavorite = bookUi.isFavorite,
                        isFinished = bookUi.isFinished,
                        isSelected = bookUi.isSelected,
                        isSelectMode = state.isSelectMode,
                        onCheckedChange = {
                            onAction(LibraryAction.ItemSelectClick(bookUi.id, bookUi.isSelected))
                        },
                        onFinishClick = {
                            onAction(LibraryAction.ItemFinishedClick(bookUi.id))
                        },
                        onFavoriteClick = {
                            onAction(LibraryAction.ItemFavoriteClick(bookUi.id))
                        },
                        onDeleteClick = {
                            onAction(LibraryAction.ItemDeleteClick(bookUi.id))
                        },
                        onShareClick = {
                            onAction(LibraryAction.ItemShareClick(bookUi.id))
                        },
                        onLongClick = {
                            onAction(LibraryAction.ItemLongClick(bookUi.id))
                        }
                    )
                }
            }
        }else{
            DataEmptyView(
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.logo),
                onImportBookClick = {
                    onAction(LibraryAction.ImportBookClick)
                },
                title = stringResource(R.string.your_library_is_empty),
                description = stringResource(R.string.your_library_is_empty_description)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun LibraryMobileScreenPreview() {
    val bookUis = remember {
        (0..10).map {
            BookUi(
                id = it,
                title = "String",
                author = "String2",
                fileUriPath = "fileUriPath",
                imageFilePath = "imageFilePath",
                isFavorite = true,
                isFinished = false,
                isSelected = it % 2 == 0
            )
        }
    }
    PageKeeperTheme {
        LibraryMobileScreen(
            state = LibraryState(
                items = bookUis,
                isShowUnsupportedDialog = false,
                isShowDeleteDialog = false,
                isSearching = false,
                isSelectMode = true
            ),
            onAction = {}
        )
    }
}