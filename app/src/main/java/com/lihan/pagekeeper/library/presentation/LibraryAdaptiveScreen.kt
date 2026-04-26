@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.library.presentation

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.components.BookSearchItem
import com.lihan.pagekeeper.core.presentation.components.BookSelectBar
import com.lihan.pagekeeper.core.presentation.components.ConfirmAlertDialog
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView
import com.lihan.pagekeeper.core.presentation.components.DeleteAlertDialog
import com.lihan.pagekeeper.core.presentation.components.PKNormalTopBar
import com.lihan.pagekeeper.core.presentation.design_system.PKCircularProgressIndicator
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.Black40
import com.lihan.pagekeeper.core.presentation.ui.theme.Divider
import com.lihan.pagekeeper.core.presentation.ui.theme.LoaderMain
import com.lihan.pagekeeper.core.presentation.ui.theme.LoaderSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TabletBlockBG
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration
import com.lihan.pagekeeper.core.presentation.util.shareUris
import com.lihan.pagekeeper.library.presentation.components.LazyBookLayout
import com.lihan.pagekeeper.library.presentation.components.LibraryTabletTopBar
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LibraryAdaptiveScreenRoot(
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    viewModel: LibraryViewModel
){
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val filePick = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null){
            viewModel.onAction(LibraryAction.UpsertBook(uri))
        }
    }

    LibraryAdaptiveScreen(
        state = state,
        onAction = { action ->
            when(action){
                LibraryAction.ImportBookClick -> {
                    filePick.launch("*/*")
                }
                LibraryAction.MenuClick -> onMenuClick()
                LibraryAction.SearchClick -> onSearchClick()
                LibraryAction.SelectMode.ShareClick -> {
                    val bookUris = state.items.filter { it.isSelected }.map { it.fileUriPath.toUri() }
                    val arraylist = arrayListOf<Uri>()
                    arraylist.addAll(bookUris)
                    context.shareUris(arraylist)
                }
                is LibraryAction.ItemShareClick -> {
                    val data = state.items.find { bookUi -> bookUi.id == action.id }
                    if (data != null){
                        context.shareUris(arrayListOf(data.fileUriPath.toUri()))
                    }
                }
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

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    BackHandler {
        focusManager.clearFocus()
        keyboard?.hide()
        onAction(LibraryAction.ClearText)
    }


    Column(
        modifier = modifier
            .then(
                if (isMobile) Modifier else
                    Modifier.clip(RoundedCornerShape(28.dp))
            )
            .fillMaxSize()
            .background(
                if (isMobile){
                    BGMain
                }else{
                    TabletBlockBG
                }
            )
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
        }
        if (!state.isSearching){
            if (state.items.isEmpty()){
                DataEmptyView(
                    isLoading = state.isLoading,
                    modifier = if (isMobile) Modifier.fillMaxSize() else Modifier,
                    logoBackgroundColor = if (isMobile) Color.Transparent else BGMain,
                    painter = painterResource(R.drawable.logo),
                    onImportBookClick = {
                        onAction(LibraryAction.ImportBookClick)
                    },
                    title = stringResource(R.string.your_library_is_empty),
                    description = stringResource(R.string.your_library_is_empty_description)
                )
            }else{
                LazyBookLayout(
                    isSelectMode = state.isSelectMode,
                    items =  state.items,
                    onFinishClick = { id,isFinish ->
                        onAction(LibraryAction.ItemFinishedClick(id,isFinish))
                    },
                    onFavoriteClick = { id, isFavorite ->
                        onAction(LibraryAction.ItemFavoriteClick(id,isFavorite))
                    },
                    onCheckedChange = { id, isSelect ->
                        onAction(LibraryAction.ItemSelectClick(id,isSelect))
                    },
                    onDeleteClick = {
                        onAction(LibraryAction.ItemDeleteClick(it))
                    },
                    onLongClick = {
                        onAction(LibraryAction.ItemLongClick(it))
                    },
                    onShareClick = {
                        onAction(LibraryAction.ItemShareClick(it))
                    }
                )

            }
        }else{
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
        state.isShowDeleteDialog && state.selectedBookUis.isNotEmpty() -> {
            DeleteAlertDialog(
                deleteItemTitle = if (state.selectedBookUis.size > 1){
                    stringResource(R.string.delete_books,state.selectedBookUis.size)
                }else{
                    stringResource(R.string.delete_book,state.selectedBookUis.first().title)
                },
                onCancel = {
                    onAction(LibraryAction.DismissDeleteDialog)
                },
                onDelete = {
                    onAction(LibraryAction.DeleteDialogConfirm)
                },
                onDismiss = {
                    onAction(LibraryAction.DismissDeleteDialog)
                }
            )
        }
    }
    if (state.isLoading){
        PKCircularProgressIndicator()
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
                    fileUriPath = "fileUriPath",
                    imageFilePath = "imageFilePath",
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
                isShowDeleteDialog = false,
                isSearching = false,
                isLoading = true
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
                    fileUriPath = "fileUriPath",
                    imageFilePath = "imageFilePath",
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
                isShowDeleteDialog = false,
                isSearching = true,
            ),
            onAction = {}
        )
    }
}