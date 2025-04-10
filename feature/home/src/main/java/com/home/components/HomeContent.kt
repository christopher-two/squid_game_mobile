package com.home.components

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.home.R
import com.home.states.HomeUiState
import com.home.screen.HomeViewModel
import com.network.firebase.models.Player
import com.shared.ui.DiamondShape
import com.shared.ui.components.BackgroundAnimated

@Composable
internal fun HomeContent(
    padding: PaddingValues,
    player: Player
) {
    LazyColumn(
        modifier = Modifier
            .background(colorScheme.onPrimary)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                Box(
                    modifier = Modifier
                        .size(400.dp)
                        .clip(DiamondShape())
                        .border(
                            width = 9.dp,
                            brush = BackgroundAnimated(
                                colorPrimary = colorScheme.primary,
                                colorSecondary = colorScheme.secondary
                            ),
                            shape = DiamondShape()
                        )
                        .background(colorScheme.onBackground)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = player.image),
                        contentDescription = "Image Player",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(colorScheme.primary.copy(alpha = .5f), blendMode = BlendMode.ColorBurn)
                    )

                    Text(
                        text = player.numPlayer,
                        color = Color.White,
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                            .graphicsLayer {
                                translationY = 40f
                                rotationX = 10f
                            }
                    )
                }
            }
        }
    )
}

@Composable
internal fun GameOver() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = .7f)),
        contentAlignment = Alignment.Center,
        content = {
            val context = LocalContext.current
            val sound = MediaPlayer.create(
                context,
                R.raw.eliminado
            )
            sound.start()
            Text(
                text = "Game Over",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                )
            )
        }
    )
}

@Composable
internal fun Winner() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green.copy(alpha = .7f)),
        contentAlignment = Alignment.Center,
        content = {
            val context = LocalContext.current
            val sound = MediaPlayer.create(
                context,
                R.raw.winning
            )
            sound.start()
            Text(
                text = "Winner",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                )
            )
        }
    )
}