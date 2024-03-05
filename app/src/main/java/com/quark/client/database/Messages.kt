package com.quark.client.database

import com.google.firebase.firestore.FirebaseFirestore
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
    private val firestore: FirebaseFirestore
) {
    /**
     * Gets all messages to a user, from a user
     * @param user1 one the participants in the chat
     * @param user2 one the participants in the chat
     * @return a list of messages
     * @throws Exception if the operation fails
     * @see QuarkMessage
     */
    /*suspend fun getConversation(user1: String, user2: String, conversationID: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        firestore.collection("messages").whereEqualTo("Document ID", conversationID).get()
            .addOnSuccessListener { documents ->
                val messages = mutableListOf<QuarkMessage>()
                for (document in documents) {
                    val userFrom = document.getString("userFrom")
                    val body = document.getString("body")

                    if (userFrom != null && body != null) {
                        messages.add(QuarkMessage(userFrom, user1, body))
                    }
                }
                continuation.resume(messages)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }

    }*/

    suspend fun getConversation(conversationID: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        val conversationRef = firestore.collection("messages").document(conversationID)
        val subcollectionRef = conversationRef.collection("1")

        subcollectionRef.get()
            .addOnSuccessListener { documents ->
                val messages = mutableListOf<QuarkMessage>()
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
