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

@Composable
fun Login(navController: NavController) {
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
            navController.navigate(Screen.Home.withArgs(password))
        }) {
            Text("Login")
        }
    }
}