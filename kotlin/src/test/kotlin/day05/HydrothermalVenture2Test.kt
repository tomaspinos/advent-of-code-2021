package day05

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class HydrothermalVenture2Test {

    @Test
    fun measure() {
        val input = TestCommon.readInput("/day05/test-input.txt")
        val result = HydrothermalVenture2().measure(input)
        assertEquals(12, result)
    }
}