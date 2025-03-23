package com.network.firebase.realtime

import com.google.firebase.database.ValueEventListener
import com.network.firebase.models.StatusPlayer

interface RealtimeDatabase {
    fun observePlayerStatus(
        playerId: String,
        callback: (StatusPlayer?) -> Unit
    ): ValueEventListener
}