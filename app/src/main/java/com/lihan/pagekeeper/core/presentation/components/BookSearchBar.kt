package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_L_Regular

@Composable
fun BookSearchBar(
    textFieldState: TextFieldState,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource?=null,
    placeholder: String?=null
) {
    BasicTextField(
        state = textFieldState,
        decorator = { innerField ->
            Box(
                contentAlignment = Alignment.CenterStart
            ){
                if (textFieldState.text.isEmpty() && placeholder != null){
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.body_L_Regular,
                        color = TextSecondary
                    )
                }
                innerField()
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = {
            onDone()
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        interactionSource = interactionSource,
        modifier = modifier
    )

}

@Preview
@Composable
private fun BookSearchBarPreview() {
    PageKeeperTheme {
        BookSearchBar(
            textFieldState = TextFieldState(initialText = ""),
            placeholder = "Search books",
            onDone = {

            }
        )
    }
}