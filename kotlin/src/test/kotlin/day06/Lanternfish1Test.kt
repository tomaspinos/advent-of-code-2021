package day06

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Lanternfish1Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day06/test-input.txt")
        val result = Lanternfish1().go(input)
        assertEquals(5934, result)
    }
}