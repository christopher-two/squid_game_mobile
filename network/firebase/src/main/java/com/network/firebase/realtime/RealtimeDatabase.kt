package com.network.firebase.realtime

import com.network.firebase.models.StatusPlayer
import kotlinx.coroutines.flow.Flow

interface RealtimeDatabase {
    fun observePlayerStatus(
        playerId: String,
    ): Flow<StatusPlayer>

    fun updatePlayerStatus(updates: Map<String, Any>, playerId: String)
}