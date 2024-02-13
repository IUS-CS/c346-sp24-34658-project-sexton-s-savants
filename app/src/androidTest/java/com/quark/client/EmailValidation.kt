package com.quark.client

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.quark.client.authentication.EmailAuth

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EmailValidation {
    @Test
    fun validEmail1() {
        val input = "thisisarealemail@gmail.com"
        val result = EmailAuth.validateEmail(input)
        assertTrue(result)
    }

    @Test
    fun validEmail2() {
        val input = "a@a.com"
        val result = EmailAuth.validateEmail(input)
        assertTrue(result)
    }

    @Test
    fun invalidEmail1() {
        val input = "thisisnotarealemail"
        val result = EmailAuth.validateEmail(input)
        assertFalse(result)
    }

    @Test
    fun invalidEmail2() {
        val input = "a@a"
        val result = EmailAuth.validateEmail(input)
        assertFalse(result)
    }

    @Test
    fun invalidEmail3() {
        val input = "a.a"
        val result = EmailAuth.validateEmail(input)
        assertFalse(result)
    }

    @Test
    fun invalidEmail4() {
        val input = "a@a."
        val result = EmailAuth.validateEmail(input)
        assertFalse(result)
    }

    @Test
    fun invalidEmail_Empty() {
        val input = ""
        val result = EmailAuth.validateEmail(input)
        assertFalse(result)
    }
}