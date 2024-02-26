package com.quark.client.navigation

/**
 * A list of screens
 *
 * Provides various screens as child objects with specific routes, as well as a method to pass args.
 */
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object SignUp : Screen("signup")
    data object Chat : Screen("chat")

    /**
     * Appends arguments to route
     *
     * @param args the list of arguments to pass.
     * @return the route with given arguments.
     */
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}