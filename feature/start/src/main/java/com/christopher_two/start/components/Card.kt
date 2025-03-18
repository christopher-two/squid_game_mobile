package com.christopher_two.start.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt

@Composable
internal fun ThreeDCard() {
    var isFrontSideVisible by remember { mutableStateOf(true) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFrontSideVisible) 0f else 180f,
        animationSpec = tween(durationMillis = 600)
    )

    val density = LocalDensity.current.density

    Box(
        modifier = Modifier
            .clickable { isFrontSideVisible = !isFrontSideVisible }
            .graphicsLayer {
                this.rotationY = rotationY
                cameraDistance = 8 * density
            }
    ) {
        if (rotationY <= 90f) {
            CardFront()
        } else {
            CardBack()
        }
    }
}

@Composable
private fun CardFront() {
    Canvas(
        modifier = Modifier
            .size(400.dp, 200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF5D28D))
            .padding(16.dp)
    ) {
        val circleRadius = size.minDimension / 5
        val squareSize = size.minDimension / 3
        val triangleHeight = sqrt(3f) / 2 * squareSize

        val centerY = size.height / 2
        val spacing = size.width / 4

        // Dibuja círculo
        drawCircle(
            color = Color.Black,
            radius = circleRadius,
            center = Offset(spacing, centerY)
        )
        drawCircle(
            color = Color(0xFFF5D28D),
            radius = circleRadius * 0.85f,
            center = Offset(spacing, centerY)
        )

        // Dibuja triángulo
        val trianglePath = Path().apply {
            moveTo(spacing * 2 - squareSize / 2, centerY + triangleHeight / 2)
            lineTo(spacing * 2 + squareSize / 2, centerY + triangleHeight / 2)
            lineTo(spacing * 2, centerY - triangleHeight / 2)
            close()
        }
        drawPath(
            path = trianglePath,
            color = Color.Black,
            style = Stroke(width = 15f),
        )

        // Dibuja cuadrado
        drawRect(
            color = Color.Black,
            style = Stroke(width = 15f),
            topLeft = Offset(spacing * 3 - squareSize / 2, centerY - squareSize / 2),
            size = Size(squareSize, squareSize)
        )
    }
}

@Composable
private fun CardBack() {
    Box(
        modifier = Modifier
            .size(400.dp, 200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFC4A46D))
            .padding(16.dp)
            .graphicsLayer {
                rotationY = 180f // Rotación adicional para el texto
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "010-241",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewThreeDCard() {
    ThreeDCard()
}