package com.christopher_two.camera

import android.Manifest
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CameraScreen(viewModel: CameraViewModel = koinViewModel()) {
    val context = LocalContext.current
    viewModel.setContext(context)
    var isCameraPermissionGranted by remember { mutableStateOf(false) }

    // Solicitar permisos de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            isCameraPermissionGranted = isGranted
        }
    )

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (isCameraPermissionGranted) {
        CameraPreview(
            viewModel = viewModel,
            controller = LifecycleCameraController(context).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE or
                            CameraController.VIDEO_CAPTURE
                )
            }
        )
    } else {
        PermissionDenied()
    }
}

@Composable
fun PermissionDenied() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Permiso de cámara denegado",
            style = typography.titleMedium,
            color = colorScheme.onBackground
        )
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    viewModel: CameraViewModel,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var result by remember { mutableStateOf("Esperando...") }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        controller.bindToLifecycle(lifecycleOwner)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Vista previa de la cámara
        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    this.controller = controller
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Resultado en la parte superior
            Text(
                text = "Resultado: $result",
                modifier = Modifier
                    .background(
                        color = colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(16.dp),
                style = typography.titleMedium,
                color = colorScheme.onPrimaryContainer
            )

            // Botón de captura
            Button(
                onClick = {
                    controller.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                super.onCaptureSuccess(image)
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val bitmap = image.toBitmap().let {
                                            Bitmap.createScaledBitmap(it, 224, 224, true)
                                        }
                                        val classificationResult = viewModel.classify(bitmap)
                                        Log.d("CameraViewModel", "Clase detectada: $classificationResult")
                                        withContext(Dispatchers.Main) {
                                            result = classificationResult
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            result = "Error: ${e.message}"
                                        }
                                    } finally {
                                        image.close()
                                    }
                                }
                            }

                            override fun onError(exception: ImageCaptureException) {
                                super.onError(exception)
                                result = "Error al capturar la imagen: ${exception.message}"
                            }
                        }
                    )
                },
                modifier = Modifier.size(80.dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Tomar foto"
                )
            }
        }
    }
}