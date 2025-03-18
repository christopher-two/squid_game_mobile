package com.christopher_two.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.christopher_two.start.components.ThreeDCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartScreen() = Screen()

@Composable
internal fun Screen(
    viewModel: StartViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
        content = {
            ThreeDCard()
        }
    )
}
