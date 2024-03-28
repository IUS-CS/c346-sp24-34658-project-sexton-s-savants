package com.quark.client.authentication

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.regex.Pattern
import kotlin.coroutines.resume

/**
 * The email authentication service.
 *
 * Authenticates using `FirebaseAuth` with email/password.
 */
object EmailAuth {
    private val auth = Firebase.auth

    /**
     * Creates a new user with the provided email and password.
     * @param email the provided email address.
     * @param password the provided password.
     * @param onResult the callback to be called when the operation is complete.
     * onResult will be called with `AuthResult.Success` if the operation was successful, and `AuthResult.Failure` otherwise.
     */
    suspend fun createUser(email: String, password: String) = suspendCancellableCoroutine { continuation ->
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    continuation.resume(AuthResult.Success)
                } else {
                  continuation.resume(AuthResult.Failure)
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
    suspend fun authenticateUser(email: String, password: String) = suspendCancellableCoroutine { continuation ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    continuation.resume(AuthResult.Success)
                } else {
                    continuation.resume(AuthResult.Failure)
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

enum class AuthResult {
    Success,
    Failure
}