package com.lihan.pagekeeper.core.presentation.design_system.checkbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.pagekeeper.core.presentation.ui.theme.BGMain
import com.lihan.pagekeeper.core.presentation.ui.theme.Icons
import com.lihan.pagekeeper.core.presentation.ui.theme.PageKeeperTheme
import com.lihan.pagekeeper.core.presentation.ui.theme.Primary

@Composable
fun PKCheckBox(
    isSelected: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = isSelected,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = CheckboxDefaults.colors(
            checkedColor = Primary,
            uncheckedColor = Icons,
            checkmarkColor = BGMain,
        )
    )
}

@Preview
@Composable
private fun PKCheckBoxPreview() {
    PageKeeperTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            PKCheckBox(
                isSelected = true,
                onCheckedChange = {}
            )
            PKCheckBox(
                isSelected = false,
                onCheckedChange = {}
            )
        }
    }
}