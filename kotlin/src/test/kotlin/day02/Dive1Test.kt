package day02

import TestCommon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Dive1Test {

    @Test
    fun dive() {
        val instructions = TestCommon.readInput("/day02/test-input.txt")
        val position = Dive1().dive(instructions)
        assertEquals(Dive1.Position(15, 10), position)
    }
}