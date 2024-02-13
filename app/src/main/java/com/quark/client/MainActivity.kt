package com.quark.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.quark.client.authentication.EmailAuth
import com.quark.client.ui.theme.QuarkClientTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: EmailAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = EmailAuth(Firebase.auth)
        setContent {
            QuarkClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuarkNavigation()
                }
            }
        }
    }
}