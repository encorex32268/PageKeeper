package com.lihan.pagekeeper.finished.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView
import com.lihan.pagekeeper.core.presentation.components.PKNormalTopBar
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration
import com.lihan.pagekeeper.library.presentation.LibraryState
import com.lihan.pagekeeper.library.presentation.components.LazyBookLayout

@Composable
fun FinishedScreen(
    state: LibraryState,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDeviceConfiguration = DeviceConfiguration.fromWindowSizeClass(
        currentWindowAdaptiveInfo().windowSizeClass
    )

    val isMobile = currentDeviceConfiguration.isMobile

    Column(
        modifier = modifier
    ) {
        PKNormalTopBar(
            title = stringResource(R.string.finished),
            onMenuClick = onMenuClick,
            onSearchClick = onSearchClick
        )
        if (state.finishedBookUis.isEmpty()){
            DataEmptyView(
                modifier = if (isMobile) Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp) else Modifier,
                painter = painterResource(R.drawable.finished_empty),
                title = stringResource(R.string.your_finished_is_empty),
                description = stringResource(R.string.your_finished_is_empty_description)
            )
        }else{
            LazyBookLayout(
                isSelectMode = state.isSelectMode,
                items = state.finishedBookUis,
                onDeleteClick = {},
                onShareClick = {},
                onLongClick = {},
                onFinishClick = { id, isFinish -> },
                onCheckedChange = { id , isSelect -> },
                onFavoriteClick = { id, isFavorite -> },
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun FinishedScreenPreview() {
    PageKeeperTheme {
        FinishedScreen(
            onSearchClick = {},
            onMenuClick = {},
            state = LibraryState()
        )
    }
}