package com.quark.client.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.quark.client.database.Messages
import com.quark.client.database.QuarkMessage
import com.quark.client.database.Users
import com.quark.client.navigation.Screen

data class ChatProps(
    val messages: Messages,
    val uid: String,
    val fromUser: String,
    val conversationID: String,
    val user: Users,
    val navController: NavController
)

@Composable
fun Chat(props: ChatProps) {
    AppBar(props)
}
@Composable
fun AppBar(props: ChatProps) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CenterAlignedTopAppBar(props)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBar(props: ChatProps) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()) { false }
    var expanded by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    var textFieldFocused by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

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
                        "Chatting with ${props.fromUser}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        props.navController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Open menu")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = {
                                // Handle action 1
                                expanded = false
                            })
                        DropdownMenuItem(
                            text = { Text("Contacts") },
                            onClick = {
                                expanded = false
                                props.navController.navigate(Screen.Contacts.route)
                            })
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                expanded = false
                                props.navController.navigate(Screen.Settings.route)
                            })
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },

        content = {
            var messages by remember {
                mutableStateOf<List<QuarkMessage>>(emptyList())
            }

            var currentUsername by remember {
                mutableStateOf("")
            }

            var updated by remember {
                mutableStateOf(false)
            }

            var scroll by remember{
                mutableStateOf(false)
            }

            LaunchedEffect(props.conversationID) {
                props.user.getUserProfileById(props.uid)?.let { user ->
                    currentUsername = user.username
                }

                val messagesFlow = props.messages.getUpdatedConversation(props.conversationID)
                messagesFlow.collect { updatedMessages ->
                    messages = updatedMessages
                    scroll = true
                }

            }

            LaunchedEffect(key1 = scroll) {
                if (scroll) {
                    listState.animateScrollToItem(messages.size)
                    scroll = false
                }
            }

            LaunchedEffect(key1 = updated) {
                if (updated) {
                    props.messages.updateConversation(
                        props.conversationID,
                        props.uid,
                        inputValue
                    )
                    inputValue = ""
                    listState.animateScrollToItem(messages.size)
                    updated = false
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(it)
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        state = listState
                    ) {
                        for (message in messages) {
                            item {
                                if (message.userFrom == props.uid)
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Blue
                                                )
                                            ) {
                                                append("${currentUsername}:")
                                            }
                                            append(" ${message.body}")
                                        }
                                    )
                                else
                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Red
                                                )
                                            ) {
                                                append("${props.fromUser}:")
                                            }
                                            append(" ${message.body}")
                                        }
                                    )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .focusRequester(focusRequester)
                            .onFocusChanged { textFieldFocused = it.isFocused },
                        value = inputValue,
                        onValueChange = { inputValue = it },
                        shape = RoundedCornerShape(28.dp),
                        maxLines = 3
                    )
                    Button(
                        modifier = Modifier
                            .height(56.dp)
                            .align(Alignment.Bottom),
                        onClick = { updated = true },
                        enabled = inputValue.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send Message"
                        )
                    }
                }
            }
        }
    )
}