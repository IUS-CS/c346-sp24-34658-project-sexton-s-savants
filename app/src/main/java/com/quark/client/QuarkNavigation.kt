package com.quark.client

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            route = Screen.Home.route,
            ) {
            Home(navController)
        }
        composable(
            route = Screen.SignUp.route,
            ) {
            SignUp(navController)
        }
    }
}