package com.christopher_two.utils.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun ButtonApp(
    @StringRes text: Int,
    modifier: Modifier,
    colorContainer: Color,
    colorContent: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        content = { Text(stringResource(text)) },
        modifier = modifier,
        shape = shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorContainer,
            contentColor = colorContent
        )
    )
}