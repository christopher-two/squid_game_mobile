package com.christopher_two.api.navcontroller

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.christopher_two.camera.CameraScreen
import com.christopher_two.login.LoginScreen
import com.christopher_two.start.StartScreen
import com.home.screen.HomeScreen
import com.shared.utils.routes.RoutesStart
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavControllerStart(
    navController: NavHostController,
    context: Context
) {
    NavHost(navController = navController, startDestination = "${RoutesStart.Home.route}/0") {
        composable(RoutesStart.Start.route) { StartScreen(navController) }
        composable(RoutesStart.Login.route) { LoginScreen(navController) }
        composable(RoutesStart.Camera.route) {
            CameraScreen(
                context = context,
                navController = navController
            )
        }
        composable(
            route = "${RoutesStart.Home.route}/{args}",
            arguments = listOf(navArgument("args") { type = StringType })
        ) { args ->
            val args = args.arguments?.getString("args").toString()
            HomeScreen(args = args)
        }
    }
}