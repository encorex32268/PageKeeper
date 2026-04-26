package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Flag
import com.lihan.pagekeeper.core.presentation.FlagFill
import com.lihan.pagekeeper.core.presentation.Share
import com.lihan.pagekeeper.core.presentation.Star
import com.lihan.pagekeeper.core.presentation.StarFill
import com.lihan.pagekeeper.core.presentation.TrashCan
import com.lihan.pagekeeper.core.presentation.design_system.buttons.PKIconButton
import com.lihan.pagekeeper.core.presentation.design_system.checkbox.PKCheckBox
import com.lihan.pagekeeper.core.presentation.ui.theme.BGCard
import com.lihan.pagekeeper.core.presentation.ui.theme.Divider
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_S_Regular
import com.lihan.pagekeeper.core.presentation.ui.theme.title_S_Medium
import kotlin.random.Random

@Composable
fun BookCard(
    title: String,
    author: String,
    imageUrl: String?,
    onCheckedChange: ((Boolean) -> Unit)?,
    onFavoriteClick: () -> Unit,
    onFinishClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    isFinished: Boolean = false,
    isSelected: Boolean = false,
    isSelectMode: Boolean = false,
    shape: Shape = RoundedCornerShape(8.dp),
) {
    val backgroundColor = remember(isSelected) {
        if (isSelected && isSelectMode){ BGCard } else{ Color.Transparent }
    }
    val borderModifier = if (isSelectMode){
        Modifier.border(
            width = 1.dp,
            color = Divider,
            shape = shape
        )
    }else{
        Modifier
    }

    val context = LocalContext.current
    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(shape)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .then(borderModifier)
            .combinedClickable(
                onLongClick = onLongClick,
                onClick = {
                    if (isSelectMode){
                        onCheckedChange?.invoke(isSelected)
                    }
                }
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelectMode){
            PKCheckBox(
                isSelected = isSelected,
                onCheckedChange = onCheckedChange
            )
        }
        if (imageUrl.isNullOrEmpty()){
            BookLoadingImage(
                modifier = Modifier
                    .width(104.dp)
                    .height(156.dp)
            )
        }else{
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(104.dp)
                    .height(156.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PKIconButton(
                    imageVector = if (isFavorite) StarFill else Star,
                    contentDescription = stringResource(R.string.book_card_favorite),
                    onClick = onFavoriteClick
                )

                PKIconButton(
                    imageVector = if (isFinished) FlagFill else Flag ,
                    contentDescription = stringResource(R.string.book_card_finish),
                    onClick = onFinishClick
                )

                PKIconButton(
                    imageVector = Share,
                    contentDescription = stringResource(R.string.book_card_share),
                    onClick = onShareClick
                )
                Spacer(Modifier.weight(1f))

                PKIconButton(
                    imageVector = TrashCan,
                    contentDescription = stringResource(R.string.book_card_delete),
                    onClick = onDeleteClick
                )

            }
        }


    }

}

@Preview(showBackground = true)
@Composable
private fun BookCardPreview() {
    PageKeeperTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BookCard(
                title = "The Adventures of Tom Sawyer",
                author = "Mark Twain",
                onCheckedChange = {},
                isSelected = Random.nextBoolean(),
                isFavorite = Random.nextBoolean(),
                isFinished = Random.nextBoolean(),
                imageUrl = "",
                onShareClick = {},
                onDeleteClick = {},
                onFinishClick = {},
                onFavoriteClick = {},
                onLongClick = {}
            )
            BookCard(
                title = "Title2",
                author = "LiHan Chen",
                onCheckedChange = {},
                isSelected = Random.nextBoolean(),
                isFavorite = Random.nextBoolean(),
                isFinished = Random.nextBoolean(),
                isSelectMode = true,
                imageUrl = null,
                onShareClick = {},
                onDeleteClick = {},
                onFinishClick = {},
                onFavoriteClick = {},
                onLongClick = {}
            )
        }
    }
}