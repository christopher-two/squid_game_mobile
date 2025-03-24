package com.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.home.R
import com.network.firebase.models.Player
import com.shared.ui.DiamondShape
import com.shared.ui.theme.AppTheme

@Composable
internal fun HomeTopBar(
    player: Player? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(colorScheme.primary),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = player?.image ?: R.drawable.baseline_person_24,
                        contentDescription = "Player Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(DiamondShape())
                            .border(2.dp,  Color.White, DiamondShape())
                            .background(
                                color = Color.White,
                            )
                    )

                    Text(
                        text = player?.numPlayer?.toString() ?: "100",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 19.sp,
                        modifier = Modifier.padding(bottom = 8.dp, end = 1.dp)
                    )
                }
            }
        }
    )
}


@Composable
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL)
private fun Preview() = AppTheme { HomeTopBar(player = Player()) }