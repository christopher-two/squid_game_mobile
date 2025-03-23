package com.network.firebase.models

data class StatusPlayer(
    val id: String,
    val isAlive: Boolean,
    val isWinner: Boolean,
    val isActive: Boolean,
)