package com.quark.client.database

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quark.client.components.showErrorDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Handles the messages in the database
 *
 * Provides methods to get messages from the database
 * @property firestore the firestore database
 */
class Messages(
    private val firestore: FirebaseFirestore,
) {

    private var messages = mutableListOf<QuarkMessage>()
    /**
     * Gets all messages to a user, from a user
     * @param conversationID the id of the chat history between users
     * @return a list of messages
     * @throws Exception if the operation fails
     * @see QuarkMessage
     */
    suspend fun getConversation(conversationID: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        val conversationRef = firestore.collection("messages").document(conversationID)
        val subcollectionRef = conversationRef.collection("1")

        messages = emptyList<QuarkMessage>().toMutableList()

        subcollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val userFrom = document.getString("userFrom")
                    val body = document.getString("Body")

                    if (userFrom != null && body != null) {
                        messages.add(QuarkMessage(userFrom, body))
                    }
                }

                continuation.resume(messages)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun updateConversation(conversationID: String, userFrom: String, body: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        val conversationRef = firestore.collection("messages").document(conversationID)
        val subcollectionRef = conversationRef.collection("1")

        val newMessage = hashMapOf(
            "Body" to body,
            "userFrom" to userFrom,
        )
        subcollectionRef.document("Message " + (messages.size + 1).toString()).set(newMessage)
            .addOnSuccessListener {
                messages.add(QuarkMessage(userFrom, body))

                continuation.resume(messages)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    /*suspend fun isUpdated(conversationID: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        val conversationRef = firestore.collection("messages").document(conversationID)
        val subcollectionRef = conversationRef.collection("1")

        subcollectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                println("nope")
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                println(snapshot.documentChanges.size)
            } else {
                println("idk")
            }
        }


    }*/
}

/**
 * Represents a message in the database
 * @property userFrom the user who sent the message
 * @property body the body of the message
 */
class QuarkMessage(
    val userFrom: String,
    val body: String,
)
