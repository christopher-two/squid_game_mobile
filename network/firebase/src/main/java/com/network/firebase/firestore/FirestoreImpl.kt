package com.network.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.network.firebase.models.Player
import kotlinx.coroutines.tasks.await

class FirestoreImpl : Firestore {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun getPlayer(id: String): Player? {
        return try {
            val documentSnapshot = db
                .collection("players")
                .document(id)
                .get()
                .await()

            documentSnapshot.toObject(Player::class.java) ?: throw Exception("No such document")
        } catch (e: Exception) {
            Log.e("FirestoreImpl", "getPlayer: ${e.message}")
            null
        }
    }
}