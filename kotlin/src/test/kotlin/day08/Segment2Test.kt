package day08

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Segment2Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day08/test-input.txt")
        val result = Segment2().go(input)
        assertEquals(61229, result)
    }
}