package day13

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TransparentOrigami1Test {

    @Test
    fun fold() {
        val input = TestCommon.readInput("/day13/test-input.txt")
        val result = TransparentOrigami1().fold(input)
        assertEquals(17, result)
    }
}