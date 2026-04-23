package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_S_Regular
import com.lihan.pagekeeper.core.presentation.ui.theme.title_S_Medium

@Composable
fun BookSearchItem(
    title: String,
    author: String,
    imageUrl: String?,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (imageUrl.isNullOrEmpty()){
            BookLoadingImage(
                modifier = Modifier
                    .size(width = 40.dp, height = 60.dp),
                shape = RoundedCornerShape(0.dp),
                contentPadding = PaddingValues(8.dp),
                logoSize = 24.dp
            )
        }else{
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .size(width = 40.dp, height = 60.dp),

            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.title_S_Medium,
                color = TextPrimary
            )
            Text(
                text = author,
                style = MaterialTheme.typography.body_S_Regular,
                color = TextSecondary
            )
        }
    }



}

@Preview
@Composable
private fun BookSearchItemPreview() {
    PageKeeperTheme {
        BookSearchItem(
            title = "Harry Potter and the Philosopher's Stone",
            author = "J.K. Rowling",
            imageUrl = null,
            onItemClick = {}
        )
    }
}