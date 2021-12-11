package day11

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DumboOctopus1Test {

    @Test
    fun score() {
        val input = TestCommon.readInput("/day11/test-input.txt")
        val result = DumboOctopus1().flash(input)
        assertEquals(1656, result)
    }
}