package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ui.theme.BGActive
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme

@Composable
fun BookCardLoadingImage(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = BGActive,
                shape = shape
            )
            .padding(horizontal = 28.dp, vertical = 54.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun BookCardLoadingImagePreview() {
    PageKeeperTheme {
        BookCardLoadingImage()
    }
}