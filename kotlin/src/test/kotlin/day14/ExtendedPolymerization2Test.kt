package day14

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ExtendedPolymerization2Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day14/test-input.txt")
        val result = ExtendedPolymerization2().go(input)
        assertEquals(2188189693529, result)
    }
}