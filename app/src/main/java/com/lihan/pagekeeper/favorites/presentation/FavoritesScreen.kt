package com.lihan.pagekeeper.favorites.presentation

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
import com.lihan.pagekeeper.library.presentation.LibraryAction

@Composable
fun FavoritesScreen(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        PKNormalTopBar(
            title = stringResource(R.string.favorites),
            onMenuClick = onMenuClick,
            onSearchClick = onSearchClick
        )
        DataEmptyView(
            painter = painterResource(R.drawable.favorites_empty),
            title = stringResource(R.string.your_favorites_is_empty),
            description = stringResource(R.string.your_favorites_is_empty_description)
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPreview() {
    PageKeeperTheme {
        FavoritesScreen(
            onSearchClick = {},
            onMenuClick = {}
        )
    }
}