package day02

import java.io.File

fun main() {
    val instructions = readInput()
    val position = Dive1().dive(instructions)
    println(position)
    println(position.horizontal * position.depth)
}

class Dive1 {

    fun dive(instructions: List<String>): Position {
        var position = Position(0, 0)

        for (instruction in instructions) {
            val split = instruction.split(" ")
            val direction = split[0]
            val difference = split[1].toInt()
            when (direction) {
                "forward" -> position = Position(position.horizontal + difference, position.depth)
                "down" -> position = Position(position.horizontal, position.depth + difference)
                "up" -> position = Position(position.horizontal, position.depth - difference)
            }
        }

        return position
    }
}

data class Position(val horizontal: Int, val depth: Int)

private fun readInput(): List<String> {
    return File(Dive1::class.java.getResource("input.txt").path)
        .readLines()
}