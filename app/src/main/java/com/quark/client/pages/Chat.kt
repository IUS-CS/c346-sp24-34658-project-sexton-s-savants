package com.quark.client.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quark.client.database.Messages
import com.quark.client.database.QuarkMessage
import com.quark.client.database.Users
import com.quark.client.navigation.Screen

data class ChatProps(
    val messages: Messages,
    val userId: String,
    val chatId: String,
)

@Composable
fun Chat(props: ChatProps) {
    var messages by remember {
        mutableStateOf<List<QuarkMessage>>(emptyList())
    }

    LaunchedEffect(key1 = props.chatId) {
        messages = props.messages.getMessagesByUserTo(props.userId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Chat Page number ${props.chatId}")
        }

        for (message in messages) {
            item {
                Text("${message.userFrom}: ${message.body}")
            }
        }
    }
}