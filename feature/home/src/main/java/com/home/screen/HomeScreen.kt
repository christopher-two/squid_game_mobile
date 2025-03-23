package com.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.home.components.FloatingActionButton
import com.home.components.HomeContent
import com.home.components.HomeTopBar
import com.home.states.HomeUiState
import com.home.viewmodel.HomeInterface
import com.home.viewmodel.HomePreview
import com.shared.ui.theme.AppTheme

@Composable
fun HomeScreen(
    args: String,
    viewModel: HomeInterface
) {
    val state by viewModel.state.collectAsState()
    Screen(
        args = args,
        viewModel = viewModel,
        state = state,
    )
}


@Composable
internal fun Screen(
    args: String,
    viewModel: HomeInterface,
    state: HomeUiState,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { HomeTopBar(player = state.player) },
        content = { padding -> HomeContent(padding = padding, viewModel = viewModel) },
        floatingActionButton = { FloatingActionButton() }
    )
}

@Composable
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL)
private fun Preview() = AppTheme {
    HomeScreen(
        args = "",
        viewModel = HomePreview()
    )
}