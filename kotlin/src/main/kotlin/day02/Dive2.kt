package day02

import Common

fun main() {
    val instructions = Common.readInput("/day02/input.txt")
    val position = Dive2().dive(instructions)
    println(position)
    println(position.horizontal * position.depth)
}

class Dive2 {

    fun dive(instructions: List<String>): Position {
        var position = Position(0, 0, 0)

        for (instruction in instructions) {
            val split = instruction.split(" ")
            val direction = split[0]
            val difference = split[1].toInt()
            when (direction) {
                "forward" -> position = Position(position.horizontal + difference, position.depth + (position.aim * difference), position.aim)
                "down" -> position = Position(position.horizontal, position.depth, position.aim + difference)
                "up" -> position = Position(position.horizontal, position.depth, position.aim - difference)
            }
        }

        return position
    }

    data class Position(val horizontal: Int, val depth: Int, val aim: Int)
}