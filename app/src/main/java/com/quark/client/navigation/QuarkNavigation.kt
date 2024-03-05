package com.quark.client.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
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
import com.quark.client.pages.LoginProps
import com.quark.client.pages.Settings
import com.quark.client.pages.SettingsProps
import com.quark.client.pages.SignUpProps

/**
 * Handles Quark's page navigation
 */
@Composable
fun QuarkNavigation() {
    val navController = rememberNavController()
    val auth = EmailAuth
    val db = Firebase.firestore
    val users = Users(db)
    val messages = Messages(db)

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            Login(
                LoginProps(navController)
            )
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
            SignUp(
                SignUpProps(navController)
            )
        }
        composable(
            route = Screen.Chat.route + "/{uid}/{fromUsername}/{conversationID}",
            arguments = listOf(
                navArgument("uid") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("fromUsername") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("conversationID") {
                    type = NavType.StringType
                    nullable = false
                }
            )) { entry ->
            Chat(
                ChatProps(
                    messages,
                    entry.arguments?.getString("uid")!!,
                    entry.arguments?.getString("fromUsername")!!,
                    entry.arguments?.getString("conversationID")!!,
                    users
                )
            )
        }
        composable(
            route = Screen.Contacts.route,
        ) {
            Contacts(
                ContactsProps(navController)
            )
        }
        composable(
            route = Screen.Settings.route,
        ) {
            Settings(
                SettingsProps(navController)
            )
        }
    }
}
