package day04

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GiantSquid1Test {

    @Test
    fun bingo() {
        val input = TestCommon.readInput("/day04/test-input.txt")
        val result = GiantSquid1().bingo(input)
        assertEquals(4512, result)
    }
}