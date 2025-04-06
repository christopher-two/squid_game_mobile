package com.home.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.home.R
import com.home.components.GameOver
import com.home.components.HomeContent
import com.home.components.Winner
import com.shared.ui.components.BackgroundAnimated
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    args: String,
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController
) {
    Screen(
        args = args,
        viewModel = viewModel,
        navController = navController
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Screen(
    args: String,
    viewModel: HomeViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.loadData(args = args)
    }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.onPrimary),
        topBar = {
            if (!state.isLoading) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Squid Game",
                            color = colorScheme.onBackground,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            fontSize = 30.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorScheme.onPrimary,
                        titleContentColor = colorScheme.onBackground
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                context.getSharedPreferences(
                                    "session",
                                    Context.MODE_PRIVATE
                                ).edit { putString("numberClass", "") }
                                navController.navigate("start") {
                                    popUpTo("start") {
                                        inclusive = true
                                    }
                                }
                            },
                            content = {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_logout_24),
                                    contentDescription = "Exit",
                                    tint = colorScheme.onBackground,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
                    },
                )
            }
        },
        content = { padding ->
            state.statusPlayer?.also { statusPlayer ->
                if (statusPlayer.isWinner == true) {
                    Winner()
                    Log.d("HomeScreen", "Winner")
                } else if (statusPlayer.isAlive == true) {
                    state.player?.also {
                        HomeContent(
                            padding = padding,
                            player = it
                        )
                    } ?: run {
                        Text(
                            text = "Loading...",
                            textAlign = TextAlign.Center,
                            color = colorScheme.onBackground
                        )
                    }
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
                        ),
                    contentAlignment = androidx.compose.ui.Alignment.Center,
                    content = {
                        Text(
                            text = "Loading...",
                            color = colorScheme.onBackground,
                            fontSize = 30.sp,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                        )
                    }
                )
            }
        }
    )
}