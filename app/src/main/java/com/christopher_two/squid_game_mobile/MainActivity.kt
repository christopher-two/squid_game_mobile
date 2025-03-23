package com.christopher_two.squid_game_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.christopher_two.api.navcontroller.NavControllerStart
import com.shared.ui.theme.AppTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
}