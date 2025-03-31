package com.christopher_two.squid_game_mobile

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.christopher_two.api.navcontroller.NavControllerStart
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.network.firebase.realtime.RealtimeDatabase
import com.network.firebase.realtime.RealtimeDatabaseImpl
import com.shared.ui.theme.AppTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onDestroy() {
        super.onDestroy()
        val updates = mapOf("isActive" to false)
        val realtimeDatabase: RealtimeDatabase = RealtimeDatabaseImpl()
        realtimeDatabase.updatePlayerStatus(
            updates = updates,
            playerId = "Yk3Rn5LN0w7GM6vVMVr3"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = colorScheme.background.luminance() < 0.5f
            )
            systemUiController.setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = colorScheme.background.luminance() < 0.5f
            )
            KoinContext {
                AppTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        NavControllerStart(
                            navController = rememberNavController(),
                            context = this.applicationContext
                        )
                    }
                }
            }
        }
    }

    private fun setupSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenViewProvider.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenViewProvider.view.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.doOnEnd { splashScreenViewProvider.remove() }
            slideUp.start()
        }
    }
}