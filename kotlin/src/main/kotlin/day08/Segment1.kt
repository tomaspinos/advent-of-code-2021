package day08

import Common

fun main() {
    val input = Common.readInput("/day08/input.txt")
    val result = Segment1().go(input)
    println(result)
}

class Segment1 {

    fun go(input: List<String>): Int {
        return input.flatMap { line -> line.split('|')[1].trim().split(' ') }
            .groupBy { signal -> signal.length }
            .filter { entry -> setOf(2, 3, 4, 7).contains(entry.key) }
            .map { entry -> entry.value.size }
            .sum()
    }
}