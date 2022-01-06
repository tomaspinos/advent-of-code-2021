package day25

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SeaCucumber1Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day25/test-input.txt")
        val result = SeaCucumber1().go(input)
        assertEquals(58, result)
    }
}