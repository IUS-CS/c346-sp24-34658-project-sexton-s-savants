package com.quark.client

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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.quark.client.authentication.EmailAuth
import androidx.compose.ui.platform.LocalContext

@Composable
fun SignUp(navController: NavController) {
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
            text = "SignUp Page"
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
            //Code validates and creates a user. gives error is user exists
            val auth = EmailAuth(Firebase.auth)

            if(EmailAuth.validateEmail(email)) {

                auth.createUser(email, password) {
                    if (it == "Created User"){
                        navController.navigate(Screen.Login.route)
                    }
                    else if (it == "Failed")
                        showErrorDialog(context, "$email is already in use.")
                }
            } else
                showErrorDialog(context, "$email is formatted incorrectly.\nFormat: Sexton@iu.edu")

        }) {
            Text("Sign Up")
        }
        Button(onClick = {
            //Code returns to login page
            navController.navigate(Screen.Login.route)
        }) {
            Text("Cancel")
        }
    }
}