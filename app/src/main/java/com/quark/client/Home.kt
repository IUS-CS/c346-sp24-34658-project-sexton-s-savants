package com.quark.client

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Home(password: String?) {
    val pass = password ?: "no password :("
    Text("BAHAHA YOUR PASSWORD IS $pass")
}