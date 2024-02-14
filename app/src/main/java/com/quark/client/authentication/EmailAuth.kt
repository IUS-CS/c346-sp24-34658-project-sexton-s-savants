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
    /**
     * Creates a new user with the provided email and password.
     * @param email the provided email address.
     * @param password the provided password.
     * @param onResult the callback to be called when the operation is complete.
     * onResult will be called with `AuthResult.Success` if the operation was successful, and `AuthResult.Failure` otherwise.
     */
    fun createUser(email: String, password: String, onResult: (AuthResult) -> Unit) {
      auth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener {task ->
              if(task.isSuccessful) {
                  onResult(AuthResult.Success)
              } else {
                  onResult(AuthResult.Failure)
              }
          }
    }

    /**
     * Authenticates a user with the provided email and password.
     * @param email the provided email address.
     * @param password the provided password.
     * @param onResult the callback to be called when the operation is complete.
     * onResult will be called with `AuthResult.Success` if the operation was successful, and `AuthResult.Failure` otherwise.
     */
    fun authenticateUser(email: String, password: String, onResult: (AuthResult) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    onResult(AuthResult.Success)
                } else {
                    onResult(AuthResult.Failure)
                }
            }
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

enum class AuthResult {
    Success,
    Failure
}