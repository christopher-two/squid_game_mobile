package com.christopher_two.camera.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PixelatedSmileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.primary)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Texto superior en coreano ("카메라를 보세요" - "Mira a la cámara")
            Text(
                text = "카메라를 보세요",
                color = colorScheme.onBackground,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Canvas para la cara pixelada animada
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                PixelatedSmileAnimation()
                Text(
                    text = "SMILE",
                    color = colorScheme.onBackground,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(x = 0.dp, y = (50).dp)
                )
            }
        }
    }
}

@Composable
fun PixelatedSmileAnimation() {
    val pixelColor = colorScheme.onBackground
    
    // Animación de la sonrisa
    val smileProgress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Canvas(modifier = Modifier.size(400.dp)) {
        val pixelSize = size.width / 30
        
        // Dibujar círculo exterior (contorno)
        val circleRadius = size.width / 2 - pixelSize * 2
        val circleCenterX = size.width / 2
        val circleCenterY = size.height / 2
        
        // Dibujar el contorno del círculo con pixeles
        for (angle in 0 until 360 step 15) {
            val radians = Math.toRadians(angle.toDouble())
            val x = (circleCenterX + circleRadius * kotlin.math.cos(radians)).toFloat()
            val y = (circleCenterY + circleRadius * kotlin.math.sin(radians)).toFloat()
            
            drawRect(
                color = pixelColor,
                topLeft = Offset(x - pixelSize/2, y - pixelSize/2),
                size = Size(pixelSize, pixelSize)
            )
        }
        
        // Dibujar ojos (2 rectángulos)
        val eyeOffset = size.width / 6
        val eyeY = circleCenterY - pixelSize * 2
        
        // Ojo izquierdo
        drawRect(
            color = pixelColor,
            topLeft = Offset(circleCenterX - eyeOffset - pixelSize/2, eyeY),
            size = Size(pixelSize * 2, pixelSize * 3)
        )
        
        // Ojo derecho
        drawRect(
            color = pixelColor,
            topLeft = Offset(circleCenterX + eyeOffset - pixelSize/2, eyeY),
            size = Size(pixelSize * 2, pixelSize * 3)
        )
        
        // Dibujar boca (línea recta que se anima a una sonrisa)
        val mouthWidth = size.width / 3
        val mouthBaseY = circleCenterY + pixelSize * 3
        
        // Cuando smileProgress es 0, la boca es una línea recta
        // Cuando smileProgress es 1, la boca es una sonrisa completa
        for (i in -5..5) {
            val x = circleCenterX + i * (mouthWidth/10)
            
            // Calcular la curva de la sonrisa basada en la posición horizontal
            val curveOffset = smileProgress * 4 * pixelSize * (1 - (i*i/25f))
            val y = mouthBaseY + curveOffset
            
            drawRect(
                color = pixelColor,
                topLeft = Offset(x - pixelSize/2, y - pixelSize/2),
                size = Size(pixelSize, pixelSize)
            )
        }
    }
}