package day10

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SyntaxScoring2Test {

    @Test
    fun score() {
        val input = TestCommon.readInput("/day10/test-input.txt")
        val result = SyntaxScoring2().score(input)
        assertEquals(288957, result)
    }
}