package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ImportBook
import com.lihan.pagekeeper.core.presentation.design_system.PKCircularProgressIndicator
import com.lihan.pagekeeper.core.presentation.design_system.buttons.PKButton
import com.lihan.pagekeeper.core.presentation.ui.theme.BGActive
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_M_Regular
import com.lihan.pagekeeper.core.presentation.ui.theme.title_L_Bold

@Composable
fun DataEmptyView(
    painter: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onImportBookClick: (() -> Unit)?=null,
    isLoading: Boolean = false,
    logoBackgroundColor: Color = BGActive
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(logoBackgroundColor,CircleShape)
                    .size(120.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = Modifier.size(72.dp),
                    painter = painter,
                    contentDescription = null
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.title_L_Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            if (!isLoading){
                Text(
                    text = description,
                    style = MaterialTheme.typography.body_M_Regular,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
            if (onImportBookClick != null){
                Spacer(Modifier.height(16.dp))
                PKButton(
                    text = stringResource(R.string.import_book),
                    onClick = onImportBookClick,
                    leadingIcon = {
                        Icon(
                            imageVector = ImportBook,
                            contentDescription = null
                        )
                    }
                )
            }

        }
    }


}

@Preview(showSystemUi = true)
@Composable
private fun DataEmptyViewPreview() {
    PageKeeperTheme {
        DataEmptyView(
            title = stringResource(R.string.your_library_is_empty),
            description = stringResource(R.string.your_library_is_empty_description),
            modifier = Modifier.fillMaxSize(),
            isLoading = false,
            onImportBookClick = {},
            painter = painterResource(R.drawable.logo)
        )
    }
}