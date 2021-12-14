package day14

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ExtendedPolymerization1Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day14/test-input.txt")
        val result = ExtendedPolymerization1().go(input)
        assertEquals(1588, result)
    }
}