package com.quark.client.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

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

    /**
     * Gets the current user.
     * @return the current user, or null if no user is signed in.
     * @see FirebaseUser
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
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

        /**
         * Checks if provided password meets requirements
         * @param password the provided password.
         * @return boolean indicating password validity
         */
        fun validatePasswordStrength (password: String): Boolean{
            val lengthRegex = ".{8,}" // at least 8 characters
            val upperCaseRegex = ".*[A-Z].*" // at least one uppercase
            val lowerCaseRegex = ".*[a-z].*" // at least one lowercase
            val numberRegex = ".*\\d.*" // at least one number
            val specialCharRegex = ".*[!@#\$%^&*].*" // at least one special character

            return Pattern.matches(lengthRegex, password) &&
                    Pattern.matches(upperCaseRegex, password) &&
                    Pattern.matches(lowerCaseRegex, password) &&
                    Pattern.matches(numberRegex, password) &&
                    Pattern.matches(specialCharRegex, password)
        }
    }
}

enum class AuthResult {
    Success,
    Failure
}