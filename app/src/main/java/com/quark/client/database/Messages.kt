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
     * @param userTo the user who received the message
     * @param userFrom the user who sent the message
     * @return a list of messages
     * @throws Exception if the operation fails
     * @see QuarkMessage
     */
    suspend fun getMessagesToFrom(userTo: String, userFrom: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        firestore.collection("messages").whereEqualTo("userTo", userTo).whereEqualTo("userFrom", userFrom).get()
            .addOnSuccessListener { documents ->
                val messages = mutableListOf<QuarkMessage>()
                for (document in documents) {
                    val userFrom = document.getString("userFrom")
                    val body = document.getString("body")

                    if (userFrom != null && body != null) {
                        messages.add(QuarkMessage(userFrom, userTo, body))
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
 * @property userTo the user who received the message
 * @property body the body of the message
 */
class QuarkMessage(
    val userFrom: String,
    val userTo: String,
    val body: String,
)
