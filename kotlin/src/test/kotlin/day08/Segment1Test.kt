package day08

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Segment1Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day08/test-input.txt")
        val result = Segment1().go(input)
        assertEquals(26, result)
    }
}