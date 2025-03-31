package com.christopher_two.start

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.christopher_two.start.components.ThreeDCard
import com.shared.utils.routes.RoutesStart
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartScreen(
    navController: NavController
) = Screen(
    viewModel = koinViewModel(),
    navController = navController
)

@Composable
internal fun Screen(
    viewModel: StartViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            ThreeDCard(
                text = "1414",
                onClick = {
                    if (state.isVisible) {
                        clipboardManager.setPrimaryClip(
                            ClipData.newPlainText("code", "1414")
                        )
                        navController.navigate(RoutesStart.Login.route)
                    } else
                        viewModel.update { copy(isVisible = !isVisible) }
                }
            )
        }
    )
}
