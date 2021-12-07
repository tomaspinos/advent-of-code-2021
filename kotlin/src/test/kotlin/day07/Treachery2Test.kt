package day07

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Treachery2Test {

    @Test
    fun cost() {
        val input = TestCommon.readInput("/day07/test-input.txt")
        val result = Treachery2().cost(input)
        assertEquals(168, result)
    }
}