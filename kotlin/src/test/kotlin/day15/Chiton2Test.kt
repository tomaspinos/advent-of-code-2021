package day15

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Chiton2Test {

    @Test
    fun lowestTotalRiskPath() {
        val input = TestCommon.readInput("/day15/test-input.txt")
        val result = Chiton2().lowestTotalRiskPath(input)
        assertEquals(315, result)
    }

    @Test
    fun readInput() {
        val input = TestCommon.readInput("/day15/test-input.txt")

        val expectedMap = TestCommon.readInput("/day15/transformed-map.txt")
            .map { line -> line.toCharArray().map { char -> Integer.parseInt(char.toString()) }.toIntArray() }
            .toTypedArray()

        val actualMap = Chiton2().readInput(input).map

        assertEquals(expectedMap.size, actualMap.size)
        assertEquals(expectedMap[0].size, actualMap[0].size)

        for (x in expectedMap.indices) {
            for (y in expectedMap.indices) {
                assertEquals(expectedMap[x][y], actualMap[x][y], "$x,$y")
            }
        }
    }
}