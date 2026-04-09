package com.lihan.pagekeeper.finished.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.components.DataEmptyView
import com.lihan.pagekeeper.core.presentation.components.PKNormalTopBar
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme

@Composable
fun FinishedScreen(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        PKNormalTopBar(
            title = stringResource(R.string.finished),
            onMenuClick = onMenuClick,
            onSearchClick = onSearchClick
        )
        DataEmptyView(
            painter = painterResource(R.drawable.finished_empty),
            title = stringResource(R.string.your_finished_is_empty),
            description = stringResource(R.string.your_finished_is_empty_description)
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun FinishedScreenPreview() {
    PageKeeperTheme {
        FinishedScreen(
            onSearchClick = {},
            onMenuClick = {}
        )
    }
}