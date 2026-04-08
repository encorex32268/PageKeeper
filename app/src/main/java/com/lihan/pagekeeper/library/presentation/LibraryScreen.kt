@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.library.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreenRoot(
    menuClick: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: LibraryViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    val filePick = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null){
            println("Get File: $it")
        }
    }

    LibraryScreen(
        state = state,
        onAction = { action ->
            when(action){
                LibraryAction.ImportBookClick -> {
                    filePick.launch("application/xml")
                }
                LibraryAction.MenuClick -> menuClick()
                LibraryAction.SearchClick -> navigateToSearch()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}


@Composable
private fun LibraryScreen(
    state: LibraryState,
    onAction: (LibraryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { it
        Column(
            modifier = Modifier
                .background(BGMain)
                .fillMaxSize()
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
                        onClick = {
                            onAction(LibraryAction.MenuClick)
                        }
                    ) {
                        Icon(
                            imageVector = Menu,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onAction(LibraryAction.SearchClick)
                        }
                    ) {
                        Icon(
                            imageVector = Search,
                            contentDescription = stringResource(R.string.search)
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
                            imageUrl = bookUi.imageUrl,
                            isFavorite = bookUi.isFavorite,
                            isFinished = bookUi.isFinished,
                            isSelected = bookUi.isSelected,
                            isSelectMode = state.isSelectMode,
                            onCheckedChange = {
                                onAction(LibraryAction.ItemSelectClick(bookUi.id,bookUi.isSelected))
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
            modifier = Modifier.fillMaxSize(),
            onImportBookClick = {
                onAction(LibraryAction.ImportBookClick)
            }
        )
    }
    if (state.isShowUnsupportedDialog){
        ConfirmAlertDialog(
            title = stringResource(R.string.unsupported_file_dialog_title),
            description = stringResource(R.string.unsupported_file_dialog_description),
            confirmText = stringResource(R.string.ok),
            onConfirm = {
                onAction(LibraryAction.DismissUnsupportedFileDialog)
            }
        )
    }
    if (state.isShowDeleteDialog && state.selectedBookUi != null){
        DeleteAlertDialog(
            deleteItemTitle = state.selectedBookUi.title,
            onCancel = {
                onAction(LibraryAction.DismissDeleteDialog)
            },
            onDelete = {
                onAction(LibraryAction.ItemDeleteClick(state.selectedBookUi.id))
            },
            onDismiss = {
                onAction(LibraryAction.DismissDeleteDialog)
            }
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
                    imageUrl = null,
                    isFavorite = true,
                    isFinished = false,
                    isSelected = true
                )
            }
        }
        LibraryScreen(
            state = LibraryState(
                isLoading = false,
                isShowUnsupportedDialog = true,
                items = bookUis,
                selectedBookUi = bookUis.random(),
                isShowDeleteDialog = false
            ),
            onAction = {}
        )
    }
}