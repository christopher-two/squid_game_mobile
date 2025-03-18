package com.christopher_two.api.navcontroller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.christopher_two.start.StartScreen
import com.christopher_two.utils.routes.RoutesStart

@Composable
fun NavControllerStart(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = RoutesStart.Start.route) {
        composable(RoutesStart.Start.route) { StartScreen(navController) }
        composable(RoutesStart.Login.route) {  }
    }
}