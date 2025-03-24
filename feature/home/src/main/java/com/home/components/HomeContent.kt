package com.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.R
import com.home.states.HomeUiState
import com.home.viewmodel.HomeInterface
import com.home.viewmodel.HomePreview
import com.home.viewmodel.HomeViewModel
import com.network.firebase.models.GameStatus
import com.network.firebase.models.Player
import com.network.firebase.models.StatusPlayer
import com.shared.ui.TextSubtitle
import com.shared.ui.TextTitle
import com.shared.ui.theme.AppTheme

@Composable
internal fun HomeContent(padding: PaddingValues, viewModel: HomeViewModel, state: HomeUiState) {
    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                BoxHome {
                    PlayerStatus(
                        player = state.player,
                        statusPlayer = state.statusPlayer,
                        gameStatus = state.gameStatus
                    )
                }
            }
        }
    )
}

@Composable
internal fun ActualGame(
    modifier: Modifier = Modifier,
    gameStatus: GameStatus?,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        TextTitle("Actual Game")
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = gameStatus?.gameIcon ?: R.drawable.outline_group_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .background(color = colorScheme.primary, shape = CircleShape)
                    .padding(5.dp)
            )
            Column(
                modifier = Modifier.padding(top = 40.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextTitle(
                    text = gameStatus?.gameActual?.name ?: "Glass Bridge",
                    fontSize = 16.sp
                )
                TextSubtitle(text = "Game #${gameStatus?.gameProgress ?: 3}")
            }
            TextSubtitle(text = gameStatus?.gameActual?.duration ?: "1h 23m")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(
                    color = colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextTitle("Game Rules:", fontSize = 16.sp)
            TextSubtitle(
                text = gameStatus?.gameActual?.gameRules
                    ?: "Las reglas son, no hay reglas."
            )
        }
    }
}