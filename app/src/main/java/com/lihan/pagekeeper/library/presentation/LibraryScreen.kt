@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Menu
import com.lihan.pagekeeper.core.presentation.Search
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.core.presentation.components.ConfirmAlertDialog
import com.lihan.pagekeeper.core.presentation.components.DeleteAlertDialog
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.library.presentation.components.LibraryEmpty
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LibraryScreenRoot(){

}


@Composable
private fun LibraryScreen(
    state: LibraryState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { it
        Column(
            modifier = Modifier
                .background(BGMain)
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() / 2)
        ) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(R.string.library),
                        style = MaterialTheme.typography.title_M_Medium,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Menu,
                            contentDescription = "menu"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Search,
                            contentDescription = "menu"
                        )
                    }
                }
            )
            if (state.items.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = state.items,
                        key = { item -> item.id}
                    ){ bookUi ->
                        BookCard(
                            title = bookUi.title,
                            author = bookUi.author,
                            imageUrl = bookUi.imageUri,
                            isFavorite = bookUi.isFavorite,
                            isFinished = bookUi.isFinished,
                            isSelected = bookUi.isSelected,
                            isSelectMode = state.isSelectMode,
                            onCheckedChange = {

                            }
                        )
                    }
                }
            }
        }

    }
    if (state.items.isEmpty()){
        LibraryEmpty(
            isLoading = state.isLoading,
            modifier = Modifier.fillMaxSize()
        )
    }
    if (state.isShowUnsupportedDialog){
        ConfirmAlertDialog(
            title = stringResource(R.string.unsupported_file_dialog_title),
            description = stringResource(R.string.unsupported_file_dialog_description),
            confirmText = stringResource(R.string.ok),
            onConfirm = {

            }
        )
    }
    if (state.isShowDeleteDialog && state.selectedBookUi != null){
        DeleteAlertDialog(
            deleteItemTitle = state.selectedBookUi.title,
            onCancel = {},
            onDelete = {},
            onDismiss = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    PageKeeperTheme {
        val bookUis = remember {
            (0..10).map {
                BookUi(
                    id = it,
                    title = "String",
                    author = "String2",
                    imageUri = null,
                    isFavorite = true,
                    isFinished = false,
                    isSelected = true
                )
            }
        }
        LibraryScreen(
            state = LibraryState(
                isLoading = false,
                isShowUnsupportedDialog = false,
                items = bookUis,
                selectedBookUi = bookUis.random(),
                isShowDeleteDialog = true
            )
        )
    }
}