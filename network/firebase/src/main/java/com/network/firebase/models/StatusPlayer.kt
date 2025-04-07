package com.network.firebase.models

data class StatusPlayer(
    var isAlive: Boolean ? = null,
    var isWinner: Boolean? = null,
    var isActive: Boolean = false,
    var image: String? = null,
    var numPlayer: String? = null,
    val uuid: String? = null
)