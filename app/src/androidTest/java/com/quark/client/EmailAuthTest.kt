package com.quark.client

import com.quark.client.authentication.AuthResult
import com.quark.client.authentication.EmailAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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
        /*
        EmailAuth.authenticateUser(email, password) { result ->
            assert(result == AuthResult.Failure)
        }
         */
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAuthenticateUserNonExisting() = runTest {
        val email = "test@gmail.com"
        val password = "password"
        // test async functions
        val result: AuthResult = EmailAuth.authenticateUser(email, password)
        assert(result == AuthResult.Failure)
    }
}