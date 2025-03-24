package com.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.R
import com.network.firebase.models.GameStatus
import com.network.firebase.models.Player
import com.network.firebase.models.StatusPlayer
import com.shared.ui.components.CanvasProgressBar

@Composable
internal fun PlayerStatus(player: Player?, statusPlayer: StatusPlayer?, gameStatus: GameStatus?) {
    val isAlive = statusPlayer?.isAlive == true
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Player Status",
                color = colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isAlive) "ALIVE" else "DEAD",
                modifier = Modifier
                    .height(25.dp)
                    .width(60.dp)
                    .background(
                        color = if (isAlive) Color(0xFF22C35D) else Color.Red,
                        shape = MaterialTheme.shapes.medium
                    ),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        Text(
            text = "Current game progress",
            color = colorScheme.tertiary,
            fontSize = 13.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Games Progress",
                color = colorScheme.onBackground,
            )
            Text(
                text = "${gameStatus?.gameProgress?:3}/${gameStatus?.gameTotal?:6}",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        CanvasProgressBar(
            gameStatus?.gameProgress?.toFloat() ?: 0.3f,
            backgroundColor = colorScheme.onBackground,
            progressColor = colorScheme.primary,
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            ContentIcon(
                icon = R.drawable.outline_group_24,
                text = "Remaining",
                number = gameStatus?.remaining?:142,
                modifier = Modifier.weight(.5f).padding(horizontal = 10.dp)
            )
            ContentIcon(
                icon = R.drawable.skull_24dp_e8eaed_fill0_wght400_grad0_opsz24,
                text = "Eliminated",
                number = gameStatus?.eliminated?:314,
                modifier = Modifier.weight(.5f).padding(horizontal = 10.dp)
            )
        }
    }
}
