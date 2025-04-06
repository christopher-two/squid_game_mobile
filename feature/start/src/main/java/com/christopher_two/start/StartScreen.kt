package com.christopher_two.start

import android.content.ClipboardManager
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val sound = MediaPlayer.create(
        LocalContext.current,
        R.raw.giro
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.onPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(
                text = "Click Card",
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                color = colorScheme.onBackground,
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ThreeDCard(
                text = "2012-5343-8676",
                onClick = {
                    if (state.isVisible) {
                        navController.navigate(RoutesStart.Login.route)
                    } else{
                        sound.start()
                        viewModel.update { copy(isVisible = !isVisible) }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.isVisible)
                Text(
                    text = "Remember the numbers",
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    color = colorScheme.onBackground,
                    fontSize = 30.sp,
                )
        }
    )
}
