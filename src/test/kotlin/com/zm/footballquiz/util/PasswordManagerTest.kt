package com.zm.footballquiz.util

import org.junit.Assert.*
import org.junit.Test

class PasswordManagerTest {

    @Test
    fun passwordLengthLessThan8ReturnFalse() {
        val actual = PasswordManager.isValidPassword("123")
        assertEquals(false, actual)
    }

    @Test
    fun passwordHasCyrillicLetterReturnFalse() {
        val actual = PasswordManager.isValidPassword("123тестTest")
        assertEquals(false, actual)
    }

    @Test
    fun passwordIsCorrectReturnTrue() {
        val actual = PasswordManager.isValidPassword("123Test11111")
        assertEquals(true, actual)
    }
}