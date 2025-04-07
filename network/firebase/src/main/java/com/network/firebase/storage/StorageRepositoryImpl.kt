package com.network.firebase.storage

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class StorageRepositoryImpl : StorageRepository {
    private val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val storageRef by lazy { storage.reference }

    private fun Bitmap.toByteArray(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        return outputStream.toByteArray()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun uploadImage(image: Bitmap): String? {
        return try {
            val uuid = Uuid.random()
            val imageData = image.toByteArray()
            val fileName = "photos/${uuid}.jpg"
            val imageRef = storageRef.child(fileName)
            val uploadTask = imageRef.putBytes(imageData).await()
            uploadTask.storage.downloadUrl.await()
            uuid.toString()
        } catch (e: Exception) {
            Log.e("StorageRepositoryImpl", "uploadImage: ${e.message}")
            null
        }
    }
}