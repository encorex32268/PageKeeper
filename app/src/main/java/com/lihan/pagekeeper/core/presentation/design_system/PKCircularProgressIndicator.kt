package com.lihan.pagekeeper.core.presentation.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.core.presentation.ui.theme.LoaderMain
import com.lihan.pagekeeper.core.presentation.ui.theme.LoaderSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme

@Composable
fun PKCircularProgressIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize()
                .size(48.dp),
            color = LoaderMain,
            trackColor = LoaderSecondary
        )
    }

}

@Preview(showSystemUi = true)
@Composable
private fun PKCircularProgressIndicatorPreview() {
    PageKeeperTheme {
        PKCircularProgressIndicator(

        )
    }
}