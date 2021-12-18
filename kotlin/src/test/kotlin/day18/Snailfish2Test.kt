package day18

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Snailfish2Test {

    val snailfish = Snailfish2()

    @Test
    fun magnitude() {
        val input = TestCommon.readInput("/day18/test-input.txt")
        val result = snailfish.magnitude(input)
        assertEquals(3993, result)
    }
}