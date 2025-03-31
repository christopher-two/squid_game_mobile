package com.christopher_two.camera.components

import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.christopher_two.camera.CameraViewModel
import com.shared.ui.DiamondShape

@Composable
internal fun CameraPreview(
    controller: LifecycleCameraController,
    viewModel: CameraViewModel
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
            .background(colorScheme.background)
            .clickable { if (isControllerReady) viewModel.takePhoto(controller) },
        content = {
            Text(
                text = "Click!!!",
                color = colorScheme.onBackground,
                fontSize = 50.sp,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
            )
            AndroidView(
                modifier = Modifier
                    .size(400.dp)
                    .background(colorScheme.background)
                    .clip(shape = DiamondShape()),
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