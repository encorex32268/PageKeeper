@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.library.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.components.ConfirmAlertDialog
import com.lihan.pagekeeper.core.presentation.components.DeleteAlertDialog
import com.lihan.pagekeeper.core.presentation.ui.theme.Black40
import com.lihan.pagekeeper.core.presentation.ui.theme.LoaderMain
import com.lihan.pagekeeper.core.presentation.ui.theme.LoaderSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun LibraryAdaptiveScreenRoot(
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    viewModel: LibraryViewModel
){
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
                    filePick.launch("application/epub+zip")
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

    if (isMobile){
        LibraryMobileScreen(
            state = state,
            onAction = onAction,
            modifier = modifier
        )
    }else{
        LibraryTabletScreen(
            state = state,
            onAction = onAction,
            modifier = modifier
        )
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
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize()
                .background(Black40)
                .wrapContentSize()
                .size(48.dp),
            color = LoaderMain,
            trackColor = LoaderSecondary
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