package com.christopher_two.camera

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class CameraViewModel(
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun update(update: CameraState.() -> CameraState) {
        _state.value = _state.value.update()
    }

    private lateinit var classLabels: List<String>

    fun loadData(
        model: String,
        labelFile: String
    ) {
        classLabels = context.assets.open(labelFile).bufferedReader().useLines { it.toList() }
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setMaxResults(1)
            .build()

        update {
            copy(
                classifier = ImageClassifier.createFromFileAndOptions(
                    context,
                    model,
                    options
                ),
            )
        }
    }

    fun classify(bitmap: Bitmap) {
        val image = TensorImage.fromBitmap(bitmap)
        val results = _state.value.classifier!!.classify(image)
        val classIndex = results[0].categories[0].index
        val className = classLabels.getOrNull(classIndex) ?: "Desconocido"
        Log.d("CameraViewModel", "Clase detectada: ${className.substring(2)}")
        val index =
            Players.entries.find { it.namePlayer == className.substring(2) }?.labelIndex ?: -1
        update {
            copy(
                classificationResult = className,
                classificationNumber = index.toString(),
            )
        }
    }

    fun takePhoto(
        controller: LifecycleCameraController
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            val bitmap = image.toBitmap().scale(224, 224)
                            classify(bitmap)
                            Log.d(
                                "CameraViewModel",
                                "Clase detectada: ${_state.value.classificationResult}"
                            )
                        } catch (e: Exception) {
                            Log.e(
                                "CameraViewModel",
                                "Error al procesar la imagen",
                                e
                            )
                        } finally {
                            image.close()
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("CameraViewModel", "Error al tomar la foto", exception)
                }
            }
        )
    }

    enum class Players(val labelIndex: Int, val namePlayer: String) {
        Player1(0, "Christhopher"),
        Player2(1, "Juan"),
        Player3(2, "Oswaldo"),
        Player4(3, "Zabdiel"),
        Player5(4, "Manuel"),
        Player6(5, "Brandon"),
        Player7(6, "Gamboa"),
        Player8(7, "Luis"),
        Player9(8, "Jesus"),
    }
}