package com.christopher_two.api.navcontroller

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.christopher_two.camera.CameraScreen
import com.christopher_two.login.LoginScreen
import com.christopher_two.start.StartScreen
import com.home.screen.HomeScreen
import com.shared.utils.enums.KeysTensorflow
import com.shared.utils.routes.RoutesStart

@Composable
fun NavControllerStart(
    navController: NavHostController,
    context: Context
) {
    NavHost(navController = navController, startDestination = "${RoutesStart.Start.route}") {
        composable(RoutesStart.Start.route) { StartScreen(navController) }
        composable(RoutesStart.Login.route) { LoginScreen(navController) }
        composable(
            route = "${RoutesStart.Camera.route}/{args}",
            arguments = listOf(navArgument("args") {
                type = NavType.EnumType<KeysTensorflow>(KeysTensorflow::class.java)
            })
        ) { args ->
            val args = args.arguments?.get("args") as KeysTensorflow
            CameraScreen(
                context = context,
                navController = navController,
                model = args
            )
        }
        composable(
            route = "${RoutesStart.Home.route}/{args}",
            arguments = listOf(navArgument("args") { type = StringType })
        ) { args ->
            val args = args.arguments?.getString("args").toString()
            Log.d("NavControllerStart", "args: $args")
            HomeScreen(args = args)
        }
    }
}