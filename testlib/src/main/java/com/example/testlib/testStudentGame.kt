package com.example.testlib

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals

import com.example.logic.StudentGame


class testStudentGame {
    lateinit var defaultStudentGame: StudentGame

    @BeforeEach
    fun createTestGrid() {
        defaultStudentGame = StudentGame(8, 10)
    }

    @Test
    fun testGetToken() {
        defaultStudentGame.mData[3][5] = 1
        defaultStudentGame.mData[1][3] = 2

        val token1 = defaultStudentGame.getToken(3, 5)
        assertEquals(1, token1)
        val token2 = defaultStudentGame.getToken(1, 3)
        assertEquals(2, token2)
        val token3 = defaultStudentGame.getToken(2, 2)
        assertEquals(0, token3)
    }

    @Test
    fun testPlayToken() {
        defaultStudentGame.mData[0][0] = 0
        defaultStudentGame.mData[1][0] = 0
        defaultStudentGame.mData[0][1] = 0
        defaultStudentGame.mData[0][2] = 0

        defaultStudentGame.playToken(0, 1)
        assertEquals(1, defaultStudentGame.mData[0][0])

        defaultStudentGame.playToken(1, 2)
        assertEquals(2, defaultStudentGame.mData[1][0])

        defaultStudentGame.playToken(0, 1)
        assertEquals(1, defaultStudentGame.mData[0][1])

        defaultStudentGame.playToken(0, 2)
        assertEquals(2, defaultStudentGame.mData[0][2])
    }
}
