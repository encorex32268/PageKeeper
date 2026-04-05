package com.lihan.pagekeeper.core.presentation.design_system.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.R
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.Primary
import com.lihan.pagekeeper.core.presentation.ui.theme.body_M_Medium

@Composable
fun PKButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = BGMain
        ),
        shape = shape
    ) {
        if (leadingIcon != null){
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.body_M_Medium,
            color = BGMain
        )
        if (trailingIcon != null){
            Spacer(modifier = Modifier.width(8.dp))
            trailingIcon()
        }
    }

}

@Preview
@Composable
private fun PKButtonPreview() {
    PageKeeperTheme {
        Column {
            PKButton(
                text = "Import Book",
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.import_book),
                        tint = BGMain,
                        contentDescription = "Import book"
                    )
                },
                onClick = {}
            )
        }
    }
}