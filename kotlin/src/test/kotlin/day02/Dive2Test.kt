package day02

import TestCommon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Dive2Test {

    @Test
    fun dive() {
        val instructions = TestCommon.readInput("/day02/test-input.txt")
        val position = Dive2().dive(instructions)
        assertEquals(Dive2.Position(15, 60, 10), position)
    }
}