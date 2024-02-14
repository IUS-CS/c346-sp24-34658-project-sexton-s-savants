package com.quark.client.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.quark.client.authentication.EmailAuth
import com.quark.client.pages.Home
import com.quark.client.pages.Login
import com.quark.client.pages.SignUp

/**
 * Handles Quark's page navigation
 */
@Composable
fun QuarkNavigation() {
    val navController = rememberNavController()
    val auth = EmailAuth(Firebase.auth)

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            Login(navController, auth)
        }
        composable(
            route = Screen.Home.route,
            ) {
            Home(navController)
        }
        composable(
            route = Screen.SignUp.route,
            ) {
            SignUp(navController, auth)
        }
    }
}