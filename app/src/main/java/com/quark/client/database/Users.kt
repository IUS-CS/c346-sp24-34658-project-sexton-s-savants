package com.quark.client.database

import com.google.firebase.firestore.FirebaseFirestore
import com.quark.client.utils.GsonHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * This class is responsible for handling all user related database operations
 * @param firestore: FirebaseFirestore
 */
class Users(
    private val firestore: FirebaseFirestore
) {
    /**
     * Gets a user's public profile from firebase using the provided uid,
     * then gets appropriate fields and converts to UserProfile
     * @param uid: String
     * @return UserProfile?
     * @throws Exception if the operation fails
     * @see UserProfile
     */
    suspend fun getUserProfileById(uid: String): UserProfile? = suspendCancellableCoroutine { continuation ->
        firestore.collection("userprofiles").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username")
                    if (username != null) {
                        val data = UserProfile(uid, username)
                        continuation.resume(data)
                    } else {
                        continuation.resume(null)
                    }
                } else {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    /**
     * Gets a user's private data from firebase as JSON using the provided uid,
     * then converts to UserData using Gson
     * @param uid: String
     * @return UserData?
     * @throws Exception if the operation fails
     * @see UserData
     * @see GsonHelper
     */
    suspend fun getUserDataById(uid: String): UserData? = suspendCancellableCoroutine { continuation ->
        firestore.collection("userdata").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.getString("data")
                    val userData = data?.let {
                        GsonHelper.gson.fromJson(it, UserData::class.java)
                    }

                    if (userData != null) {
                        continuation.resume(userData)
                    } else {
                        continuation.resume(null)
                    }
                } else {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}

/**
 * Represents a user's public profile in the database
 * @property userId the user's id
 * @property username the user's username
 */
class UserProfile(
    val userId: String,
    val username: String,
)

/**
 * Represents a user's private data in the database
 * @property activeConversations a list of active conversations
 */
data class UserData(
    val activeConversations: List<String>
)
