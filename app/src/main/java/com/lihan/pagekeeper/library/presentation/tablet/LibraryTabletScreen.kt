package com.lihan.pagekeeper.library.presentation.tablet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Close
import com.lihan.pagekeeper.core.presentation.Search
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.core.presentation.components.BookSearchBar
import com.lihan.pagekeeper.core.presentation.components.BookSearchItem
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.Divider
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TabletBlockBG
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.core.presentation.util.preview_provider.PreviewData
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView

@Composable
fun LibraryTabletScreen(
    state: LibraryTabletState,
    onAction: (LibraryTabletAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(TabletBlockBG,RoundedCornerShape(28.dp))
            .padding(top = 12.dp)
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (state.isSearching){
                        Modifier.fillMaxWidth()
                    }else{
                        Modifier.width(292.dp)
                    }
                )
                .background(BGMain,RoundedCornerShape(28.dp))
                .clip(RoundedCornerShape(28.dp)) ,
            verticalAlignment = Alignment.CenterVertically
        ){
            BookSearchBar(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                textFieldState = state.searchTextField,
                placeholder = stringResource(R.string.search_bar_placeholder),
                onDone = {

                },
            )
            IconButton(
                modifier = Modifier.padding(end = 4.dp),
                onClick = {
                    if (state.searchTextField.text.isNotEmpty()){
                        onAction(LibraryTabletAction.CleanText)
                    }else{
                        onAction(LibraryTabletAction.StartSearch)
                    }
                }
            ) {
                Icon(
                    imageVector = if (state.isSearching){
                        Close
                    }else{
                        Search
                    },
                    contentDescription = null,
                    tint = Icons
                )
            }
        }
        when{
            !state.isSearching && state.items.isEmpty() -> {
                DataEmptyView(
                    isLoading = state.isLoading,
                    onImportBookClick = {},
                    logoBackgroundColor = BGMain,
                    painter = painterResource(R.drawable.logo),
                    title = stringResource(R.string.your_library_is_empty),
                    description = stringResource(R.string.your_library_is_empty_description)
                )
            }
            state.isSearching -> {
                HorizontalDivider(
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                    thickness = 1.dp,
                    color = Divider
                )
                if (state.searchedItems.isEmpty()){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        text = stringResource(R.string.no_results_found),
                        style = MaterialTheme.typography.title_M_Medium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }else{
                    LazyVerticalGrid(
                        modifier = Modifier.weight(1f),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(state.searchedItems){ bookUi ->
                            BookSearchItem(
                                title = bookUi.title,
                                author = bookUi.author,
                                imageUrl = bookUi.imageUrl,
                                onItemClick = {

                                }
                            )
                        }
                    }
                }
            }
            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(2)
                ) {
                    items(
                        items = state.items,
                        key = { item -> item.id}
                    ){ bookUi ->
                        BookCard(
                            title = bookUi.title,
                            author = bookUi.author,
                            imageUrl = bookUi.imageUrl,
                            isFavorite = bookUi.isFavorite,
                            isFinished = bookUi.isFinished,
                            isSelected = bookUi.isSelected,
                            isSelectMode = state.isSelectMode,
                            onCheckedChange = {
                                onAction(LibraryTabletAction.ItemSelectClick(bookUi.id,bookUi.isSelected))
                            },
                            onFinishClick = {
                                onAction(LibraryTabletAction.ItemFinishedClick(bookUi.id))
                            },
                            onFavoriteClick = {
                                onAction(LibraryTabletAction.ItemFavoriteClick(bookUi.id))
                            },
                            onDeleteClick = {
                                onAction(LibraryTabletAction.ItemDeleteClick(bookUi.id))
                            },
                            onShareClick = {
                                onAction(LibraryTabletAction.ItemShareClick(bookUi.id))
                            }
                        )
                    }
                }

            }
        }

    }

}


@Preview(showBackground = true, device = "id:pixel_9_pro_fold")
@Composable
private fun LibraryTabletScreenPreview() {

    PageKeeperTheme {
        LibraryTabletScreen(
            state = LibraryTabletState(
                isSearching = false,
                searchedItems = PreviewData.searchBookUis,
                items = PreviewData.bookUis
            ),
            onAction = {}
        )
    }
}