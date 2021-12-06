package day06

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Lanternfish2Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day06/test-input.txt")
        val result = Lanternfish2().go(input)
        assertEquals(26984457539, result)
    }
}