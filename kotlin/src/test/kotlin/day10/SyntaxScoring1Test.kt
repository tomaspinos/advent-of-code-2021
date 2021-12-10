package day10

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SyntaxScoring1Test {

    @Test
    fun score() {
        val input = TestCommon.readInput("/day10/test-input.txt")
        val result = SyntaxScoring1().score(input)
        assertEquals(26397, result)
    }
}