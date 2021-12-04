package day04

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GiantSquid2Test {

    @Test
    fun bingo() {
        val input = TestCommon.readInput("/day04/test-input.txt")
        val result = GiantSquid2().bingo(input)
        assertEquals(1924, result)
    }
}