package com.lihan.pagekeeper.library.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.Close
import com.lihan.pagekeeper.core.presentation.Search
import com.lihan.pagekeeper.core.presentation.components.BookSearchBar
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme

@Composable
fun LibraryTabletTopBar(
    searchTextField: TextFieldState,
    isSearching: Boolean,
    onCleanTextClick: () -> Unit,
    onStartSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocus) {
        if (isFocus && !isSearching){
            onStartSearchClick()
        }
    }


    Row(
        modifier = modifier
            .systemBarsPadding()
            .then(
                if (isSearching){
                    Modifier.fillMaxWidth()
                }else{
                    Modifier.width(292.dp)
                }
            )
            .background(BGMain,RoundedCornerShape(28.dp))
            .clip(RoundedCornerShape(28.dp)) ,
        verticalAlignment = Alignment.CenterVertically
    ){
        BookSearchBar(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            textFieldState = searchTextField,
            placeholder = stringResource(R.string.search_bar_placeholder),
            interactionSource = interactionSource,
            onDone = {

            },
        )
        IconButton(
            modifier = Modifier.padding(end = 4.dp),
            onClick = {
                if (isSearching){
                    onCleanTextClick()
                }else{
                    onStartSearchClick()
                }
            }
        ) {
            Icon(
                imageVector = if (isSearching){
                    Close
                }else{
                    Search
                },
                contentDescription = null,
                tint = Icons
            )
        }
    }
}


@Preview
@Composable
private fun LibraryTabletTopBarPreview() {
    PageKeeperTheme {
        LibraryTabletTopBar(
            searchTextField = TextFieldState(),
            isSearching = true,
            onCleanTextClick = {},
            onStartSearchClick = {}
        )
    }
}