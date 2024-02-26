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
     * Gets the messages from firebase using the provided userTo,
     * then gets appropriate fields and converts to QuarkMessage
     *
     * @param userTo the user to get messages for
     * @return the list of messages for the user
     *
     * @throws Exception if the messages cannot be retrieved
     */
    suspend fun getMessagesByUserTo(userTo: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        firestore.collection("messages").whereEqualTo("userTo", userTo).get()
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
