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
import com.network.firebase.storage.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class CameraViewModel(
    private val context: Context,
    private val storageRepository: StorageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun update(update: CameraState.() -> CameraState) {
        _state.value = _state.value.update()
    }

    private lateinit var classLabels: List<String>

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
                            val uuid = storageRepository.uploadImage(bitmap)
                            uuid?.let { uuid ->
                                update {
                                    copy(
                                        image = bitmap,
                                        uuid = uuid
                                    )
                                }
                            }
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
}