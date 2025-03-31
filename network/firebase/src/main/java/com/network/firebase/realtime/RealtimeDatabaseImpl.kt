package com.network.firebase.realtime

import android.util.Log
import com.google.firebase.Firebase // Puedes usar esta o la otra, sé consistente
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database // KTX helper
import com.google.firebase.database.getValue // KTX helper
import com.network.firebase.models.StatusPlayer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimeDatabaseImpl : RealtimeDatabase {
    private val database = FirebaseDatabase.getInstance()

    override fun observePlayerStatus(
        playerId: String,
    ): Flow<StatusPlayer> = callbackFlow {
        Log.d("RealtimeDB", "Observing player status for playerId: $playerId")
        val playersRef = database.getReference("players").child(playerId)
        Log.d("RealtimeDB", "playersRef: $playersRef")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("RealtimeDB", "Snapshot received: $snapshot")
                try {
                    val isAlive = snapshot.child("isAlive").getValue(Boolean::class.java) ?: false
                    val isWinner = snapshot.child("isWinner").getValue(Boolean::class.java) ?: false
                    val isActive = snapshot.child("isActive").getValue(Boolean::class.java) ?: false

                    val status = StatusPlayer(isAlive, isWinner, isActive)
                    Log.d("RealtimeDB", "Deserialized Status: $status")
                    trySend(status).isSuccess
                } catch (e: Exception) {
                    Log.e("RealtimeDB", "Error deserializing data: ${e.message}", e)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("RealtimeDB", "Firebase listener cancelled: ${error.message}", error.toException())
                close(error.toException())
            }
        }

        Log.d("RealtimeDB", "Attaching listener to $playersRef")
        playersRef.addValueEventListener(listener)
        awaitClose {
            Log.d("RealtimeDB", "Removing listener from $playersRef")
            playersRef.removeEventListener(listener)
        }
    }

    override fun updatePlayerStatus(updates: Map<String, Any>, playerId: String) {
        val playerRef = database.getReference("players").child(playerId)
        playerRef.updateChildren(updates) // Actualiza solo los campos especificados
            .addOnSuccessListener {
                Log.d("Firebase", "Campo(s) actualizado(s): ${updates.keys}")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error al actualizar: ${e.message}")
            }
    }
}