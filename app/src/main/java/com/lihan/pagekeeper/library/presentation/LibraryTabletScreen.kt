package com.lihan.pagekeeper.library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.core.presentation.components.BookSearchItem
import com.lihan.pagekeeper.core.presentation.components.BookSelectBar
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.Divider
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TabletBlockBG
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.library.presentation.components.LibraryTabletTopBar
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LibraryTabletScreen(
    state: LibraryState,
    onAction: (LibraryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .fillMaxSize()
            .background(TabletBlockBG)
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
            LibraryTabletTopBar(
                modifier = Modifier
                    .then(
                        if (state.isSearching){
                            Modifier.fillMaxWidth()
                        }else{
                            Modifier
                        }
                    )
                    .padding(12.dp),
                searchTextField = state.searchTextField,
                isSearching = state.isSearching,
                onCleanTextClick = {
                    onAction(LibraryAction.ClearText)
                },
                onStartSearchClick = {
                    onAction(LibraryAction.StartSearch)
                }
            )
        }
        when{
            !state.isSearching -> {
                if (state.items.isEmpty()){
                    DataEmptyView(
                        isLoading = state.isLoading,
                        onImportBookClick = {
                            onAction(LibraryAction.ImportBookClick)
                        },
                        logoBackgroundColor = BGMain,
                        painter = painterResource(R.drawable.logo),
                        title = stringResource(R.string.your_library_is_empty),
                        description = stringResource(R.string.your_library_is_empty_description)
                    )
                }else{
                    LazyVerticalGrid(
                        modifier = modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                }
            }
            state.isSearching ->{
                Column {
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 8.dp),
                        thickness = 1.dp,
                        color = Divider
                    )
                    when{
                        state.searchedItems.isEmpty() && state.searchTextField.text.isEmpty() -> Unit
                        state.searchedItems.isEmpty() && state.searchTextField.text.isNotEmpty() -> {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp),
                                text = stringResource(R.string.no_results_found),
                                style = MaterialTheme.typography.title_M_Medium,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                        else ->{
                            LazyVerticalGrid(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 16.dp),
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.searchedItems){ bookUi ->
                                    BookSearchItem(
                                        title = bookUi.title,
                                        author = bookUi.author,
                                        imageUrl = bookUi.imageFilePath,
                                        onItemClick = {

                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_9_pro_fold")
@Composable
private fun LibraryTabletScreenPreview() {
    PageKeeperTheme {
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
                    isSelected = true
                )
            }
        }
        LibraryTabletScreen(
            state = LibraryState(
                items = bookUis,
                isShowUnsupportedDialog = false,
                isShowDeleteDialog = false,
                isSearching = false,
                searchedItems = bookUis.filter { it.id >= 5 },
                isSelectMode = true

            ),
            onAction = {}
        )
    }

}