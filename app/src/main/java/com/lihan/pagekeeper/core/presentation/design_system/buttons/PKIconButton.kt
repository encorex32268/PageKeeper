package com.lihan.pagekeeper.core.presentation.design_system.buttons

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Share
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme

@Composable
fun PKIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tintColor: Color = Icons,
    contentDescription: String?=null
) {
    IconButton(
        modifier = modifier
            .defaultMinSize(-1.dp,-1.dp)
            .size(20.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tintColor
        )
    }
}

@Preview
@Composable
private fun PKIconButtonPreview() {
    PageKeeperTheme {
        PKIconButton(
            imageVector = Share,
            onClick = {}
        )
    }
}