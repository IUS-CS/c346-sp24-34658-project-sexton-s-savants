package com.quark.client.pages

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quark.client.authentication.EmailAuth
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.quark.client.navigation.Screen
import com.quark.client.authentication.AuthResult
import com.quark.client.components.showErrorDialog

@Composable
fun Login(navController: NavController, auth: EmailAuth) {
    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            text = "Login Page"
        )
        OutlinedTextField(
            value = email,
            singleLine = true,
            placeholder = {
                Text("Email")
            },
            onValueChange = {
                    text -> email = text
            })
        OutlinedTextField(
            value = password,
            singleLine = true,
            placeholder = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                    text -> password = text
            })
        Button(onClick = {
            //Code authenticates user trying to log in.
            if(!EmailAuth.validateEmail(email)) {
                showErrorDialog(context, "$email is formatted incorrectly.\nFormat: Sexton@iu.edu")
            }

            auth.authenticateUser(email, password){
                if (it == AuthResult.Success) {
                    navController.navigate(Screen.Home.route)
                } else if (it == AuthResult.Failure){
                    showErrorDialog(context, "Please check the entered fields.")
                }
            }

        }) {
            Text("Login")
        }
        Button(onClick = {
            navController.navigate(Screen.SignUp.route)
        }) {
            Text("Sign Up")
        }
    }
}