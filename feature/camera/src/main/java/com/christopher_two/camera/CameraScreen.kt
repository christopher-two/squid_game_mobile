package com.christopher_two.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.christopher_two.camera.components.CameraPreview
import com.christopher_two.camera.components.PermissionDenied
import com.christopher_two.camera.components.Permissions
import com.christopher_two.camera.components.PixelatedSmileScreen
import com.shared.utils.enums.KeysTensorflow
import com.shared.utils.routes.RoutesStart
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel = koinViewModel(),
    context: Context,
    navController: NavController,
) {
    var isCompleteAnimation by remember { mutableStateOf(true) }
    val state by viewModel.state.collectAsState()
    val hasFrontCamera = remember(context) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        try {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    val controller = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = if (hasFrontCamera) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(4000)
        isCompleteAnimation = false
    }

    LaunchedEffect(controller) {
        controller.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
    }

    Permissions(viewModel = viewModel)

    if (isCompleteAnimation)
        PixelatedSmileScreen()
    else if (state.isCameraPermissionGranted)
        CameraPreview(
            viewModel = viewModel,
            controller = controller
        )
    else PermissionDenied()

    state.uuid?.let { uuid ->
        navController.navigate(
            route = "${RoutesStart.Home.route}/${uuid}",
        )
    }
}
