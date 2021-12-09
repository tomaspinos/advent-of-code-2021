package day09

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SmokeBasin2Test {

    @Test
    fun riskLevel() {
        val input = TestCommon.readInput("/day09/test-input.txt")
        val result = SmokeBasin2().riskLevel(input)
        assertEquals(1134, result)
    }
}