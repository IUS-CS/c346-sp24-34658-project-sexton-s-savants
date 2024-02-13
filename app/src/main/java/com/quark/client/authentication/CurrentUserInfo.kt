package com.quark.client.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * The current user info
 *
 * Authenticates using `FirebaseAuth` with email/password.
 */
class CurrentUserInfo(
    private val auth: FirebaseAuth

) {
    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}