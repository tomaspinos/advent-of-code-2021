package day09

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SmokeBasin1Test {

    @Test
    fun riskLevel() {
        val input = TestCommon.readInput("/day09/test-input.txt")
        val result = SmokeBasin1().riskLevel(input)
        assertEquals(15, result)
    }
}