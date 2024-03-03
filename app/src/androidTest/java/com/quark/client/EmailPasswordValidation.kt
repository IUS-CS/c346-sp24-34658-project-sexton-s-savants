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
class EmailPasswordValidation {
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

    @Test
    fun validPassword1() {
        val input = "ULO3@i08"
        val result = EmailAuth.validatePasswordStrength(input)
        assertTrue(result)
    }

    @Test
    fun invalidPassword_Empty() {
        val input = ""
        val result = EmailAuth.validatePasswordStrength(input)
        assertFalse(result)
    }

    @Test
    fun invalidPassword_TooShort() {
        val input = "%i8I"
        val result = EmailAuth.validatePasswordStrength(input)
        assertFalse(result)
    }

    @Test
    fun invalidPassword_NoSpecialChar() {
        val input = "3hgkhexL"
        val result = EmailAuth.validatePasswordStrength(input)
        assertFalse(result)
    }

    @Test
    fun invalidPassword_NoNumber() {
        val input = "\$Arzxoqk"
        val result = EmailAuth.validatePasswordStrength(input)
        assertFalse(result)
    }

    @Test
    fun invalidPassword_NoUppercase() {
        val input = "6opnqen!"
        val result = EmailAuth.validatePasswordStrength(input)
        assertFalse(result)
    }

    @Test
    fun invalidPassword_NoLowercase() {
        val input = "&\$F5L96A"
        val result = EmailAuth.validatePasswordStrength(input)
        assertFalse(result)
    }
}