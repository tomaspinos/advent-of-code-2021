package day12

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PassagePathing2Test {

    @Test
    fun paths1() {
        val input = TestCommon.readInput("/day12/test-input1.txt")
        val result = PassagePathing2().paths(input)
        assertEquals(36, result)
    }

    @Test
    fun paths2() {
        val input = TestCommon.readInput("/day12/test-input2.txt")
        val result = PassagePathing2().paths(input)
        assertEquals(103, result)
    }

    @Test
    fun paths3() {
        val input = TestCommon.readInput("/day12/test-input3.txt")
        val result = PassagePathing2().paths(input)
        assertEquals(3509, result)
    }
}