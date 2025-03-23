package com.shared.utils.routes

sealed class RoutesStart(val route: String) {
    data object Start : RoutesStart(route = "start")
    data object Home : RoutesStart(route = "home")
    data object Login : RoutesStart(route = "login")
    data object Camera : RoutesStart(route = "camera")
}