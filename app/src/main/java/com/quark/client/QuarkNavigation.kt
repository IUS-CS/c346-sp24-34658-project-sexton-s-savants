package com.quark.client

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * Handles Quark's page navigation
 */
@Composable
fun QuarkNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            Login(navController)
        }
        composable(
            route = Screen.Home.route + "/{password}",
            arguments = listOf(
            navArgument("password") {
                type = NavType.StringType
                defaultValue = "password"
                nullable = true
            }
        )) { entry ->
            Home(password = entry.arguments?.getString("password"))
        }
    }
}