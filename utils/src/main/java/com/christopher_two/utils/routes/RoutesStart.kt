package com.christopher_two.utils.routes

sealed class RoutesStart(val route: String) {
    data object Start : RoutesStart(route = "start")
    data object Home : RoutesStart(route = "home")
}