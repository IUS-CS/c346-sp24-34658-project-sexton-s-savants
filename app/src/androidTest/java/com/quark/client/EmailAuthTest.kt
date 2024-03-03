package com.quark.client

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.quark.client.authentication.AuthResult
import com.quark.client.authentication.EmailAuth
import org.junit.Before
import org.junit.Test

class EmailAuthTest {
    @Test
    fun testCreateUser() {
        val email = "test@gmail.com"
        val password = "password"
        EmailAuth.createUser(email, password) { result ->
            assert(result == AuthResult.Success)

            val user = EmailAuth.getCurrentUser()
            assert(user != null)
            assert(user?.email == email)
        }
    }

    @Test
    fun testCreateUserInvalid() {
        val email = "invalid"
        val password = "password"
        EmailAuth.createUser(email, password) { result ->
            assert(result == AuthResult.Failure)

            val user = EmailAuth.getCurrentUser()
            assert(user == null)
        }
    }

    @Test
    fun testCreateUserAlreadyExists() {
        val email = "test@gmail.com"
        val password = "password"
        EmailAuth.createUser(email, password) { result ->
            if (result == AuthResult.Success) {
                EmailAuth.createUser(email, password) { result ->
                    assert(result == AuthResult.Failure)
                }
            }
        }
    }

    @Test
    fun testAuthenticateUser() {
        val email = "test@gmail.com"
        val password = "password"
        EmailAuth.createUser(email, password) {
            EmailAuth.authenticateUser(email, password) { result ->
                assert(result == AuthResult.Success)
            }
        }
    }

    @Test
    fun testAuthenticateUserNonExisting() {
        val email = "test@gmail.com"
        val password = "password"
        EmailAuth.authenticateUser(email, password) { result ->
            assert(result == AuthResult.Failure)
        }
    }
}