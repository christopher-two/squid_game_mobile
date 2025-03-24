package com.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.components.FloatingActionButton
import com.home.components.HomeContent
import com.home.components.HomeTopBar
import com.home.states.HomeUiState
import com.home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    args: String,
    viewModel: HomeViewModel = koinViewModel()
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
    viewModel: HomeViewModel,
    state: HomeUiState,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { HomeTopBar(player = state.player) },
        content = { padding ->
            HomeContent(
                padding = padding,
                viewModel = viewModel,
                state = state
            )
        },
        floatingActionButton = { FloatingActionButton() },
        bottomBar = { BottomAppBarContent() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBarContent() {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .offset(y = (40).dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.TopCenter,
        content = {
            Button(
                modifier = Modifier.fillMaxWidth(.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    scope.launch {
                        if (isSheetOpen) bottomSheetState.hide() else bottomSheetState.show()
                        isSheetOpen = !isSheetOpen
                    }
                },
                content = {
                    Text(
                        text = "Player Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    )

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { bottomSheetState.hide() }
        },
        sheetState = bottomSheetState,
    ) {

    }
}
