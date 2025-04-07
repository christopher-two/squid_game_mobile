package com.network.firebase.storage

import android.graphics.Bitmap

interface StorageRepository {
    suspend fun uploadImage(image: Bitmap): String?
}