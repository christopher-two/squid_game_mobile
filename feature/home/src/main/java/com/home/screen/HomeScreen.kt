package com.home.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.home.components.GameOver
import com.home.components.HomeContent
import com.home.components.Winner
import com.home.viewmodel.HomeViewModel
import com.shared.ui.components.BackgroundAnimated
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    args: String,
    viewModel: HomeViewModel = koinViewModel()
) {
    Screen(
        args = args,
        viewModel = viewModel,
    )
}


@Composable
internal fun Screen(
    args: String,
    viewModel: HomeViewModel,
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background),
        content = { padding ->
            state.statusPlayer?.also { statusPlayer ->
                if (statusPlayer.isWinner == true) {
                    Winner()
                    Log.d("HomeScreen", "Winner")
                } else if (statusPlayer.isAlive == true) {
                    HomeContent(
                        padding = padding,
                        viewModel = viewModel,
                        state = state
                    )
                    Log.d("HomeScreen", "Alive")
                } else {
                    GameOver()
                    Log.d("HomeScreen", "GameOver")
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = BackgroundAnimated(
                                colorPrimary = colorScheme.primary,
                                colorSecondary = colorScheme.secondary
                            )
                        )
                )
            }
        }
    )
}