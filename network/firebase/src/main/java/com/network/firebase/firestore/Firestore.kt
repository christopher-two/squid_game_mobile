package com.network.firebase.firestore

import com.network.firebase.models.Player

interface Firestore {
    suspend fun getPlayer(id: String): Player?
    suspend fun createPlayer(player: Player)
}