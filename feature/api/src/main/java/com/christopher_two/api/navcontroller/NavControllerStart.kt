package com.christopher_two.api.navcontroller

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.christopher_two.camera.CameraScreen
import com.christopher_two.login.LoginScreen
import com.christopher_two.start.StartScreen
import com.christopher_two.utils.routes.RoutesStart

@Composable
fun NavControllerStart(
    navController: NavHostController,
    context: Context
) {
    NavHost(navController = navController, startDestination = RoutesStart.Start.route) {
        composable(RoutesStart.Start.route) { StartScreen(navController) }
        composable(RoutesStart.Login.route) { LoginScreen(navController) }
        composable(RoutesStart.Camera.route) { CameraScreen() }
    }
}