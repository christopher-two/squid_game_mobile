package com.christopher_two.squid_game_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.christopher_two.api.navcontroller.NavControllerStart
import com.christopher_two.squid_game_mobile.ui.theme.Squid_game_mobileTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                Squid_game_mobileTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavControllerStart(
                            navController = rememberNavController()
                        )
                    }
                }
            }
        }
    }
}