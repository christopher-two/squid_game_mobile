package com.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.home.viewmodel.HomeInterface

@Composable
internal fun HomeContent(padding: PaddingValues, viewModel: HomeInterface) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    )
}