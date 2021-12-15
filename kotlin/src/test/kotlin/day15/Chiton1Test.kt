package day15

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Chiton1Test {

    @Test
    fun lowestTotalRiskPath() {
        val input = TestCommon.readInput("/day15/test-input.txt")
        val result = Chiton1().lowestTotalRiskPath(input)
        assertEquals(40, result)
    }
}