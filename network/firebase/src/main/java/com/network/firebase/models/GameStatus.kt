package com.network.firebase.models

import androidx.annotation.DrawableRes

data class GameStatus(
    val gameTotal: Int = 6,
    val gameProgress: Int = 3,
    val remaining: Int = 142,
    val eliminated: Int = 314,
    val games: List<Game> = emptyList(),
    val gameActual: Game? = null,
    val gameNext: Game? = null,
    @DrawableRes val gameIcon: Int?
)