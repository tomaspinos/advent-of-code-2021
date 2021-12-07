package day07

import Common
import kotlin.math.abs

fun main() {
    val input = Common.readInput("/day07/input.txt")
    val result = Treachery2().cost(input)
    println(result)
}

class Treachery2 {

    fun cost(input: List<String>): Int {
        val positions = input.first().split(',').map(String::toInt)

        val minPosition = positions.minOrNull()!!
        val maxPosition = positions.maxOrNull()!!

        return (minPosition..maxPosition).minOf { position -> positionCost(position, positions) }
    }

    private fun positionCost(targetPosition: Int, positions: List<Int>): Int {
        return positions.sumOf { position -> stepCost(abs(position - targetPosition)) }
    }

    private fun stepCost(steps: Int): Int {
        return (1..steps).sum()
    }
}