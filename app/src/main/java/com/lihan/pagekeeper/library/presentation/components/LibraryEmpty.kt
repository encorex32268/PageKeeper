package com.lihan.pagekeeper.library.presentation.components

import android.widget.TextClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ImportBook
import com.lihan.pagekeeper.core.presentation.components.PKCircularProgressIndicator
import com.lihan.pagekeeper.core.presentation.design_system.buttons.PKButton
import com.lihan.pagekeeper.core.presentation.ui.theme.BGActive
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_M_Regular
import com.lihan.pagekeeper.core.presentation.ui.theme.title_L_Bold

@Composable
fun LibraryEmpty(
    isLoading: Boolean,
    modifier: Modifier = Modifier
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
                    .background(BGActive,CircleShape)
                    .size(120.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = Modifier.size(72.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.your_library_is_empty),
                style = MaterialTheme.typography.title_L_Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            if (!isLoading){
                Text(
                    text = stringResource(R.string.your_library_is_empty_description),
                    style = MaterialTheme.typography.body_M_Regular,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(16.dp))
            PKButton(
                text = stringResource(R.string.import_book),
                onClick = {},
                leadingIcon = {
                    Icon(
                        imageVector = ImportBook,
                        contentDescription = null
                    )
                }
            )
        }
        if (isLoading){
            PKCircularProgressIndicator()
        }

    }


}

@Preview(showSystemUi = true)
@Composable
private fun LibraryEmptyPreview() {
    PageKeeperTheme {
        LibraryEmpty(
            modifier = Modifier.fillMaxSize(),
            isLoading = true
        )
    }
}