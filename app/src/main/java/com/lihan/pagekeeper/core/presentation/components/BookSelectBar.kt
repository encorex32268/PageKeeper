@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ArrowLeft
import com.lihan.pagekeeper.core.presentation.Share
import com.lihan.pagekeeper.core.presentation.Star
import com.lihan.pagekeeper.core.presentation.TrashCan
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import com.lihan.pagekeeper.core.presentation.util.preview_provider.PreviewData
import com.lihan.pagekeeper.library.presentation.model.BookUi

@Composable
fun BookSelectBar(
    selectedSize: Int,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = ArrowLeft,
                    contentDescription = null,
                    tint = Icons
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.selected_books,selectedSize),
                style = MaterialTheme.typography.title_M_Medium,
                color = TextPrimary
            )
        },
        actions = {
            IconButton(
                onClick = onToggleFavorite
            ) {
                Icon(
                    imageVector = Star,
                    contentDescription = null,
                    tint = Icons
                )
            }
            IconButton(
                onClick = onShareClick
            ) {
                Icon(
                    imageVector = Share,
                    contentDescription = null,
                    tint = Icons
                )
            }
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = TrashCan,
                    contentDescription = null,
                    tint = Icons
                )
            }
        }
    )

}


@Preview(showBackground = true)
@Composable
private fun BookSelectBarPreview() {
    PageKeeperTheme {
        BookSelectBar(
            selectedSize = PreviewData.bookUis.size,
            onShareClick = {},
            onDeleteClick = {},
            onToggleFavorite = {},
            onBack = {}
        )
    }
}