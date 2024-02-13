package com.quark.client

/**
 * A list of screens
 *
 * Provides various screens as child objects with specific routes, as well as a method to pass args.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")

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