package com.christopher_two.camera

import android.Manifest
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.christopher_two.camera.components.PixelatedSmileAnimation
import com.christopher_two.camera.components.PixelatedSmileScreen
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CameraScreen(viewModel: CameraViewModel = koinViewModel()) {
    val context = LocalContext.current
    var isCompleteAnimation by remember { mutableStateOf(true) }
    val state by viewModel.state.collectAsState()
    val controller = remember { LifecycleCameraController(context) }

    LaunchedEffect(Unit) {
        delay(4000)
        isCompleteAnimation = false
    }

    LaunchedEffect(controller) {
        controller.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
    }

    Permissions(viewModel = viewModel)

    if (isCompleteAnimation) {
        PixelatedSmileScreen()
    } else if (state.isCameraPermissionGranted) {
        CameraPreview(
            viewModel = viewModel,
            state = state,
            controller = controller
        )
    } else {
        PermissionDenied()
    }
}

@Composable
fun Permissions(
    viewModel: CameraViewModel
) {
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> viewModel.update { copy(isCameraPermissionGranted = isGranted) } }
    )

    LaunchedEffect(Unit) { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
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
    state: CameraState
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var isControllerReady by remember { mutableStateOf(false) }
    DisposableEffect(lifecycleOwner) {
        controller.bindToLifecycle(lifecycleOwner)
        isControllerReady = true
        onDispose {
            controller.unbind()
            isControllerReady = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.onSecondary)
            .clickable { if (isControllerReady) viewModel.takePhoto(controller) },
        content = {
            AndroidView(
                modifier = Modifier
                    .size(400.dp)
                    .background(colorScheme.onSecondary)
                    .clip(shape = CircleShape),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                        )
                        this.controller = controller
                    }
                },
            )
        }
    )
}

@Composable
@Preview
fun Preview() {

}