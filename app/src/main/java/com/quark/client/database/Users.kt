package com.quark.client.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Users(
    private val firestore: FirebaseFirestore
) {
    /* Gets the user from firebase using the provided uid,
    * then gets appropriate fields and converts to UserData */
    suspend fun getUserById(uid: String): UserData? = suspendCancellableCoroutine { continuation ->
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username")
                    if (username != null) {
                        continuation.resume(UserData(username))
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
    /* Updates the user in firebase using the provided uid,
* then updates the appropriate fields
     */
//    fun createUser(uid: String, email: String, username: String, )
}

class UserData(
    val username: String,
)
