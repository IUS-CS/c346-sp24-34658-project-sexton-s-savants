package com.quark.client.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.quark.client.authentication.EmailAuth
import com.quark.client.database.Messages
import com.quark.client.database.Users
import com.quark.client.pages.Chat
import com.quark.client.pages.ChatProps
import com.quark.client.pages.Home
import com.quark.client.pages.HomeProps
import com.quark.client.pages.Login
import com.quark.client.pages.SignUp
import com.quark.client.pages.Contacts
import com.quark.client.pages.ContactsProps
import com.quark.client.pages.Settings
import com.quark.client.pages.SettingsProps

/**
 * Handles Quark's page navigation
 */
@Composable
fun QuarkNavigation() {
    val navController = rememberNavController()
    val auth = EmailAuth(Firebase.auth)
    val db = Firebase.firestore
    val users = Users(db)
    val messages = Messages(db)

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            Login(navController, auth)
        }
        composable(
            route = Screen.Home.route,
            ) {
            Home(
                HomeProps(navController, users, auth.getCurrentUser()?.uid ?: "")
            )
        }
        composable(
            route = Screen.SignUp.route,
            ) {
            SignUp(navController, auth)
        }
        composable(
            route = Screen.Chat.route + "/{chatId}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { entry ->
            Chat(
                ChatProps(
                    messages,
                    auth.getCurrentUser()?.uid!!,
                    entry.arguments?.getString("chatId")!!
                )
            )
        }
        composable(
            route = Screen.Contacts.route,
        ) {
            Contacts(
                ContactsProps(navController, auth)
            )
        }
        composable(
            route = Screen.Settings.route,
        ) {
            Settings(
                SettingsProps(navController, auth)
            )
        }
    }
}