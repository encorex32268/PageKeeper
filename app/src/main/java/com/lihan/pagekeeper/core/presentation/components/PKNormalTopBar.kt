@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Menu
import com.lihan.pagekeeper.core.presentation.Search
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.core.presentation.util.DeviceConfiguration

@Composable
fun PKNormalTopBar(
    title: String,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDeviceConfiguration = DeviceConfiguration.fromWindowSizeClass(
        currentWindowAdaptiveInfo().windowSizeClass
    )

    if (currentDeviceConfiguration.isMobile){
        CenterAlignedTopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.title_M_Medium,
                    color = TextPrimary
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onMenuClick
                ) {
                    Icon(
                        imageVector = Menu,
                        contentDescription = stringResource(R.string.menu)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onSearchClick
                ) {
                    Icon(
                        imageVector = Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        )

    }

}


@Preview(showBackground = true)
@Composable
private fun PKNormalTopBarPreview() {
    PageKeeperTheme {
        PKNormalTopBar(
            title = "Title",
            onMenuClick = {},
            onSearchClick = {}
        )
    }
}