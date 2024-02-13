package com.quark.client.authentication

import com.google.firebase.auth.FirebaseAuth

/**
 * The email authentication service.
 *
 * Authenticates using `FirebaseAuth` with email/password.
 */
class EmailAuth(
    private val auth: FirebaseAuth
) {
    fun createUser(email: String, password: String, onResult: (Throwable?) -> Unit) {
      auth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener { onResult(it.exception) }
    }

    fun authenticateUser(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    companion object {
        /**
         * Checks if provided email address is valid
         * @param email the provided email address.
         * @return boolean indicating email validity
         */
        fun validateEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}