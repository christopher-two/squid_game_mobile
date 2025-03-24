package com.shared.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Bold
) = Text(
    text = text,
    color = color,
    fontSize = fontSize,
    fontWeight = fontWeight,
)

@Composable
fun TextSubtitle(
    text: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    fontSize: TextUnit = 13.sp,
    fontWeight: FontWeight = FontWeight.Normal
) = Text(
    text = text,
    color = color,
    fontSize = fontSize,
    fontWeight = fontWeight,
)