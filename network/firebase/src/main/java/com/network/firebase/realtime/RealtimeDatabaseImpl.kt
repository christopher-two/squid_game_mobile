package com.network.firebase.realtime

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.network.firebase.models.StatusPlayer

class RealtimeDatabaseImpl : RealtimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val playersRef = database.getReference("players")

    override fun observePlayerStatus(
        playerId: String,
        callback: (StatusPlayer?) -> Unit
    ): ValueEventListener {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.getValue(StatusPlayer::class.java)
                callback(status)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RealtimeDB", "Error: ${error.message}")
                callback(null)
            }
        }

        playersRef.child(playerId).addValueEventListener(listener)
        return listener
    }

    fun removeListener(listener: ValueEventListener) {
        playersRef.removeEventListener(listener)
    }
}