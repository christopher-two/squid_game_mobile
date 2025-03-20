package com.christopher_two.camera

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class CameraViewModel(
    private val context: Context
) : ViewModel() {

    // Estado de la cámara
    data class CameraState(
        val image: Bitmap? = null,
        val classificationResult: String = "Esperando...",
        val context: Context? = null
    )

    // Flujo de estado
    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    // Clasificador de imágenes
    private val classifier: ImageClassifier

    init {
        // Configuración del clasificador
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(1) // Solo queremos el resultado más probable
            .build()

        classifier = ImageClassifier.createFromFileAndOptions(
            context,
            "model.tflite", // Asegúrate de que el modelo esté en la carpeta assets
            options
        )
    }

    fun setContext(context: Context) {
        _state.value = _state.value.copy(context = context)
    }

    fun classify(bitmap: Bitmap): String {
        val image = TensorImage.fromBitmap(bitmap)
        val results = classifier.classify(image)
        return if (results.isNotEmpty() && results[0].categories.isNotEmpty()) {
            results[0].categories[0].label // Devuelve la clase detectada
        } else {
            "No se detectó ninguna clase"
        }
    }
}