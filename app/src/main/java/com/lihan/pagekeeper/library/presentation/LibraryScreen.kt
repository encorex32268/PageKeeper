@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.library.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Menu
import com.lihan.pagekeeper.core.presentation.Search
import com.lihan.pagekeeper.core.presentation.components.BookCard
import com.lihan.pagekeeper.core.presentation.components.BookSearchItem
import com.lihan.pagekeeper.core.presentation.components.ConfirmAlertDialog
import com.lihan.pagekeeper.core.presentation.components.DeleteAlertDialog
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView
import com.lihan.pagekeeper.core.presentation.components.PKNormalTopBar
import com.lihan.pagekeeper.core.presentation.ui.theme.Divider
import com.lihan.pagekeeper.core.presentation.ui.theme.TabletBlockBG
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration
import com.lihan.pagekeeper.library.presentation.components.LibraryAdaptive
import com.lihan.pagekeeper.library.presentation.components.LibraryTabletTopBar
import com.lihan.pagekeeper.library.presentation.model.BookUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreenRoot(
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
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

    LibraryAdaptiveScreen(
        state = state,
        onAction = { action ->
            when(action){
                LibraryAction.ImportBookClick -> {
                    filePick.launch("application/xml")
                }
                LibraryAction.MenuClick -> onMenuClick()
                LibraryAction.SearchClick -> onSearchClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun LibraryAdaptiveScreen(
    state: LibraryState,
    onAction: (LibraryAction) -> Unit,
    modifier: Modifier = Modifier
){
    val currentDeviceConfiguration = DeviceConfiguration.fromWindowSizeClass(
        currentWindowAdaptiveInfo().windowSizeClass
    )

    val isMobile = currentDeviceConfiguration.isMobile

    Column(
        modifier = modifier.fillMaxSize()
            .background(if (isMobile) BGMain else TabletBlockBG)
    ) {
        if (isMobile){
            PKNormalTopBar(
                title = stringResource(R.string.library),
                onMenuClick = {
                    onAction(LibraryAction.MenuClick)
                },
                onSearchClick = {
                    onAction(LibraryAction.SearchClick)
                }
            )
        }else{
            LibraryTabletTopBar(
                searchTextField = state.searchTextField,
                isSearching = state.isSearching,
                onCleanTextClick = {
                    onAction(LibraryAction.CleanText)
                },
                onStartSearchClick = {
                    onAction(LibraryAction.StartSearch)
                }
            )
        }
        if (!state.isSearching){
            LibraryAdaptive(
                isMobile = isMobile,
                onDeleteClick = {
                    onAction(LibraryAction.ItemDeleteClick(it))
                },
                onFinishClick = {
                    onAction(LibraryAction.ItemFinishedClick(it))
                },
                onShareClick = {
                    onAction(LibraryAction.ItemShareClick(it))
                },
                onFavoriteClick = {
                    onAction(LibraryAction.ItemFavoriteClick(it))
                },
                onCheckedChange = { id,boolean ->
                   onAction(LibraryAction.ItemSelectClick(id,boolean))
                },
                isSelectMode = state.isSelectMode,
                items = state.items
            )
        }else if (!isMobile){
            Column {
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
                        modifier = modifier.weight(1f),
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
        }
    }

    when{
       state.isShowUnsupportedDialog ->{
            ConfirmAlertDialog(
                title = stringResource(R.string.unsupported_file_dialog_title),
                description = stringResource(R.string.unsupported_file_dialog_description),
                confirmText = stringResource(R.string.ok),
                onConfirm = {
                    onAction(LibraryAction.DismissUnsupportedFileDialog)
                }
            )
        }
        state.isShowDeleteDialog && state.selectedBookUi != null -> {
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
        !state.isSearching && state.items.isEmpty() -> {
            if (isMobile){
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
            }else{
                DataEmptyView(
                    isLoading = state.isLoading,
                    onImportBookClick = {},
                    logoBackgroundColor = BGMain,
                    painter = painterResource(R.drawable.logo),
                    title = stringResource(R.string.your_library_is_empty),
                    description = stringResource(R.string.your_library_is_empty_description)
                )
            }
        }

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
        LibraryAdaptiveScreen(
            state = LibraryState(
                items = bookUis,
                isShowUnsupportedDialog = false,
                selectedBookUi = bookUis.random(),
                isShowDeleteDialog = false,
                isSearching = false
            ),
            onAction = {}
        )
    }
}


@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_9_pro_fold")
@Composable
private fun LibraryScreenTabletPreview() {
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
        LibraryAdaptiveScreen(
            state = LibraryState(
                items = bookUis,
                isShowUnsupportedDialog = false,
                selectedBookUi = bookUis.random(),
                isShowDeleteDialog = false,
                isSearching = true

            ),
            onAction = {}
        )
    }
}