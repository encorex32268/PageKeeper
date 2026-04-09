package com.lihan.pagekeeper.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.TextPrimary
import com.lihan.pagekeeper.core.presentation.ui.theme.TextSecondary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_M_Medium
import com.lihan.pagekeeper.core.presentation.ui.theme.body_M_Regular
import com.lihan.pagekeeper.core.presentation.ui.theme.title_M_Medium

@Composable
fun ConfirmAlertDialog(
    title: String,
    description: String,
    confirmText: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp)
) {

    Dialog(
        onDismissRequest = onConfirm
    ) {
        Column(
            modifier = modifier
                .widthIn(max = 312.dp)
                .clip(shape)
                .background(color = BGMain,shape)
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.title_M_Medium,
                color = TextPrimary
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.body_M_Regular,
                color = TextSecondary
            )
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                TextButton(
                    onClick = onConfirm
                ) {
                    Text(
                        text = confirmText,
                        style = MaterialTheme.typography.body_M_Medium,
                        color = TextPrimary
                    )

                }
            }
        }
    }

}

@Preview
@Composable
private fun ConfirmAlertDialogPreview() {
    PageKeeperTheme {
        ConfirmAlertDialog(
            title = "Unsupported file format",
            description = "Please select a book in FB2 format.",
            confirmText = "OK",
            onConfirm = {}
        )
    }
}