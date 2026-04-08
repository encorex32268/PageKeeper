@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.pagekeeper.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ArrowLeft
import com.lihan.pagekeeper.core.presentation.Close
import com.lihan.pagekeeper.core.presentation.components.BookSearchBar
import com.lihan.pagekeeper.core.presentation.components.BookSearchItem
import com.lihan.pagekeeper.core.presentation.ui.theme.Divider
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreenRoot(
    onBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchScreen(
        state = state,
        onAction = { action ->
            when(action){
                SearchAction.OnBackClick -> onBack()
                is SearchAction.OnItemClick -> {
                    onNavigateToDetail(action.id)
                }
                SearchAction.OnDoneClick,
                SearchAction.OnCloseClick -> {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { it
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onAction(SearchAction.OnBackClick)
                        }
                    ) {
                        Icon(
                            imageVector = ArrowLeft,
                            contentDescription = null,
                            tint = Icons
                        )
                    }

                },
                title = {
                    BookSearchBar(
                        textFieldState = state.searchTextFieldState,
                        placeholder = stringResource(R.string.search_bar_placeholder),
                        onDone = {
                            onAction(SearchAction.OnDoneClick)
                        }
                    )
                },
                actions = {
                    if (state.searchTextFieldState.text.isNotEmpty()){
                        IconButton(
                            onClick = {
                                onAction(SearchAction.OnCloseClick)
                            }
                        ) {
                            Icon(
                                imageVector = Close,
                                contentDescription = null,
                                tint = Icons
                            )
                        }
                    }
                }
            )
            HorizontalDivider(
                color = Divider,
                thickness = 1.dp
            )
            if (state.items.isEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    text = stringResource(R.string.no_results_found),
                    style = MaterialTheme.typography.title_M_Medium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }else{
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.items){ searchBookUi ->
                        BookSearchItem(
                            title = searchBookUi.title,
                            author = searchBookUi.author,
                            imageUrl = searchBookUi.imageUrl,
                            onItemClick = {
                                onAction(SearchAction.OnItemClick(searchBookUi.id))
                            }
                        )
                    }
                }
            }
        }

    }

}

@Preview
@Composable
private fun SearchScreenPreview() {
    PageKeeperTheme {
        SearchScreen(
            state = SearchState(
                searchTextFieldState = TextFieldState(initialText = "")
            ),
            onAction = {}
        )
    }
}