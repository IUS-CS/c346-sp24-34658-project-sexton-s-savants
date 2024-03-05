package com.quark.client.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quark.client.database.Conversation
import com.quark.client.database.Messages
import com.quark.client.database.QuarkMessage
import com.quark.client.database.UserProfile
import com.quark.client.database.Users

data class ChatProps(
    val messages: Messages,
    val uid: String,
    val fromUsername: String,
    val conversationID: String,
    val users: Users,
)

@Composable
fun Chat(props: ChatProps) {
    var messages by remember {
        mutableStateOf<List<QuarkMessage>>(emptyList())
    }

    LaunchedEffect(key1 = props.conversationID, key2 = props.users) {
        props.messages.getConversation(props.conversationID).map { message ->
            val usernameFrom = props.users.getUserProfileById(message.userFrom)?.username.toString()
            QuarkMessage(usernameFrom, message.body)
        }.also {messages = it }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Chatting with ${props.fromUsername}", fontWeight = FontWeight.Bold)
        }

        for (message in messages) {
            item {
                Text("${message.userFrom}: ${message.body}")
            }
        }
    }
}