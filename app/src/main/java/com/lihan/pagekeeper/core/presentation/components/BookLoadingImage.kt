package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ui.theme.BGActive
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme

@Composable
fun BookLoadingImage(
    modifier: Modifier = Modifier,
    logoSize: Dp = 48.dp,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 28.dp,
        vertical = 54.dp
    ),
    shape: Shape = RoundedCornerShape(4.dp)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = BGActive,
                shape = shape
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.size(logoSize),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun BookLoadingImagePreview() {
    PageKeeperTheme {
        BookLoadingImage()
    }
}