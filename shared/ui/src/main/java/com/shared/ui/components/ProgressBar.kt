package com.shared.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CanvasProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF2A2A2A),
    progressColor: Color = Color.White,
    animationDurationMillis: Int = 1000
) {
    // Animate the progress value
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = animationDurationMillis),
        label = "progressAnimation"
    )

    Canvas(
        modifier = modifier
            .height(12.dp)
            .fillMaxWidth()
    ) {
        // Draw background
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(8f, 8f),
            size = Size(size.width, size.height)
        )

        // Draw progress
        drawRoundRect(
            color = progressColor,
            cornerRadius = CornerRadius(8f, 8f),
            size = Size(size.width * animatedProgress, size.height)
        )
    }
}