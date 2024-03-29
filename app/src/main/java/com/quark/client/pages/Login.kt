package com.quark.client.pages

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
import androidx.compose.ui.platform.LocalContext
import com.quark.client.navigation.Screen
import com.quark.client.authentication.AuthResult
import com.quark.client.components.showErrorDialog

data class LoginProps(
    val navController: NavController,
)

@Composable
fun Login(props: LoginProps) {
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
            if((email.isEmpty() || !EmailAuth.validateEmail(email)) || password.isEmpty()) {
                showErrorDialog(context, "Please enter all fields correctly.")
            }else{
                EmailAuth.authenticateUser(email, password){
                    if (it == AuthResult.Success) {
                        props.navController.navigate(Screen.Home.route)
                    } else if (it == AuthResult.Failure){
                        showErrorDialog(context, "Please check the entered credentials.")
                    }
                }
            }

        }) {
            Text("Login")
        }
        Button(onClick = {
            props.navController.navigate(Screen.SignUp.route)
        }) {
            Text("Sign Up")
        }
    }
}