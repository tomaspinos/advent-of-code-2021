package day11

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DumboOctopus2Test {

    @Test
    fun score() {
        val input = TestCommon.readInput("/day11/test-input.txt")
        val result = DumboOctopus2().flash(input)
        assertEquals(195, result)
    }
}