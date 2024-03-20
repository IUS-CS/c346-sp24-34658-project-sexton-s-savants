package com.quark.client.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quark.client.authentication.EmailAuth
import com.quark.client.database.Users
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import com.quark.client.navigation.Screen
import com.quark.client.authentication.AuthResult
import com.quark.client.components.showErrorDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine

data class SignUpProps(
    val firestore: FirebaseFirestore,
    val auth: EmailAuth,
    val users: Users,
    val navController: NavController

)

@Composable
fun SignUp(props: SignUpProps) {
    val context = LocalContext.current


    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var updated by remember {
        mutableStateOf(false)
    }

    /*LaunchedEffect(key1 = updated) {
        if(updated) {
            if(props.users.usernameUsedQuery(username) == true) {
                showErrorDialog(context,
                    "Username is already in use. Try another.")
            }
            updated = false
        }
    }*/

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            text = "Sign Up Page"
        )
        OutlinedTextField(
            value = username,
            singleLine = true,
            placeholder = {
                Text("Username")
            },
            onValueChange = {
                    text -> username = text
            })
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
            //Code validates and creates a user. gives error if user exists
            if((email.isEmpty() || !EmailAuth.validateEmail(email)) || (password.isEmpty() || !EmailAuth.validatePasswordStrength(password))) {
                showErrorDialog(context,
                    "Email or Password is formatted incorrectly.\n\n" +
                            "Email Format: Sexton@iu.edu\n\n" +

                            "Password Format: \n" +
                            "- At least 8 characters long\n" +
                            "- Contains at least one uppercase letter\n" +
                            "- Contains at least one lowercase letter\n" +
                            "- Contains at least one number\n" +
                            "- Contains at least one special character"
                )
            }

            else if (!props.users.vertifyUsername(username)) {
                showErrorDialog(context,
                    "Username is formatted incorrectly.\n\n" +
                            "Username cannot exceed 16 character or include an '@'.")
            }

            else {
                updated = true
                props.auth.createUser(email, password) {
                    if (it == AuthResult.Success){
                        props.navController.navigate(Screen.Login.route)
                    } else {
                        showErrorDialog(context, "$email is already in use.")
                    }
                }
            }

        }) {
            Text("Sign Up")
        }
        Button(onClick = {
            //Code returns to login page
            props.navController.navigate(Screen.Login.route)
        }) {
            Text("Cancel")
        }
    }
}