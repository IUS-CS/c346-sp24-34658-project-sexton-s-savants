package com.quark.client.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quark.client.database.UserProfile
import com.quark.client.database.Users
import com.quark.client.navigation.Screen

data class HomeProps(
    val navController: NavController,
    val users: Users,
    val userId: String
)

@Composable
fun Home(props: HomeProps) {
    AppBar(props)
}
@Composable
fun AppBar(props: HomeProps) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CenterAlignedTopAppBar(props)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBar(props: HomeProps) {
    var username by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = props.userId) {
        props.users.getUserProfileById(props.userId)?.let { user ->
            username = user.username
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Welcome, $username, to QUARK",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        props.navController.navigate(Screen.Login.route)}) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Sign Out"
                        )
                    }
                },
                //TODO make dropdown menu here
                actions = {
                    IconButton(onClick = { /* add code to create drop down menu */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Options"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },

        floatingActionButton = {
            LargeFloatingActionButton(
                //TODO add functionality to create new chat here
                onClick = {/*add code to create new chat here*/},
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Send, "Create new chat.")
            }
        }
    ) { innerPadding ->
        ScrollContent(innerPadding, props)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues, props: HomeProps) {
    var activeConversations by remember {
        mutableStateOf<List<UserProfile>>(emptyList())
    }

    LaunchedEffect(key1 = props.userId) {
        props.users.getUserDataById(props.userId)?.let { user ->
            user.activeConversations.map { userId ->
                props.users.getUserProfileById(userId) ?: UserProfile(userId, "Unknown")
            }.also { activeConversations = it }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (user in activeConversations) {
            item {
                TextButton(onClick = {
                    props.navController.navigate(Screen.Chat.withArgs(user.userId, user.username))
                }) {
                    Text(text = user.username, fontSize = 30.sp)
                }
            }
        }
    }
}

