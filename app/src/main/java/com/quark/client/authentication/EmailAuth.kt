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
    fun createUser(email: String, password: String, onResult: (String) -> Unit) {
      auth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener {task ->
              if(task.isSuccessful)
                  onResult("Created User")
              else
                  onResult("Failed")
          }
    }

    fun authenticateUser(email: String, password: String, onResult: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful)
                    onResult("Authenticated User")
                else
                    onResult("Failed")
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

        private const val TAG = "EmailPassword"

    }
}