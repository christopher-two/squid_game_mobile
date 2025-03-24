package com.home.states

import com.network.firebase.models.GameStatus
import com.network.firebase.models.Player
import com.network.firebase.models.StatusPlayer

data class HomeUiState(
    val player: Player? = null,
    val statusPlayer: StatusPlayer? = null,
    val gameStatus: GameStatus? = null,
)