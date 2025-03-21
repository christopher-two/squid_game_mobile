package com.christopher_two.camera

import android.graphics.Bitmap
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

data class CameraState(
    val image: Bitmap? = null,
    val classificationResult: String = "Esperando...",
    val classifier: ImageClassifier? = null,
    val isLoading: Boolean = true,
    val isCameraPermissionGranted: Boolean = false
)