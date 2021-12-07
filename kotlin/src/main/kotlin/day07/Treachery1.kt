package day07

import Common
import kotlin.math.abs

fun main() {
    val input = Common.readInput("/day07/input.txt")
    val result = Treachery1().cost(input)
    println(result)
}

class Treachery1 {

    fun cost(input: List<String>): Int {
        val positions = input.first().split(',').map(String::toInt).sorted()

        val medianPosition = positions[(positions.size / 2) - ((positions.size % 2) * 1)]

        return positions.sumOf { position -> abs(position - medianPosition) }
    }
}