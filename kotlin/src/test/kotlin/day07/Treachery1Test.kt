package day07

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Treachery1Test {

    @Test
    fun cost() {
        val input = TestCommon.readInput("/day07/test-input.txt")
        val result = Treachery1().cost(input)
        assertEquals(37, result)
    }
}