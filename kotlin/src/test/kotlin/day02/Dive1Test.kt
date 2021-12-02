package day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class Dive1Test {

    @Test
    fun dive() {
        val instructions = readInput()
        val position = Dive1().dive(instructions)
        assertEquals(Position(15, 10), position)
    }

    private fun readInput(): List<String> {
        return File(this::class.java.getResource("test-input.txt").path)
            .readLines()
    }
}