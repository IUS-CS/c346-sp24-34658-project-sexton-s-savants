package com.quark.client.database

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firestore.admin.v1.Index
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
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

    suspend fun updateConversation(conversationID: String, userFrom: String, body: String): List<QuarkMessage> = suspendCancellableCoroutine { continuation ->
        val conversationRef = firestore.collection("messages").document(conversationID)
        val subcollectionRef = conversationRef.collection("1")

        val newMessage = hashMapOf(
            "Time" to FieldValue.serverTimestamp(),
            "Body" to body,
            "userFrom" to userFrom,
        )
        subcollectionRef.add(newMessage)
            .addOnSuccessListener {
                messages.add(QuarkMessage(userFrom, body))
                continuation.resume(messages)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun getUpdatedConversation(conversationID: String) = callbackFlow<List<QuarkMessage>> {
        val conversationRef = firestore.collection("messages").document(conversationID)
        val subcollectionRef = conversationRef.collection("1").orderBy("Time", Query.Direction.ASCENDING)

        val listener = subcollectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val newMessages = mutableListOf<QuarkMessage>() // Create a new list for storing messages

            snapshot?.documents?.forEach { document ->
                val userFrom = document.getString("userFrom")
                val body = document.getString("Body")
                if (userFrom != null && body != null) {
                    newMessages.add(QuarkMessage(userFrom, body)) // Add new messages to the new list
                }
            }

            trySend(newMessages).isSuccess // Send the new list of messages
        }

        awaitClose { listener.remove() }
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
