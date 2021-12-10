package day10

import Common
import java.util.*

fun main() {
    val input = Common.readInput("/day10/input.txt")
    val result = SyntaxScoring1().score(input)
    println(result)
}

class SyntaxScoring1 {

    private val validPairs: List<ValidPair> = listOf(
        ValidPair('(', ')', 3),
        ValidPair('[', ']', 57),
        ValidPair('{', '}', 1197),
        ValidPair('<', '>', 25137)
    )

    fun score(input: List<String>): Int {
        return input.sumOf { line -> score(line) }
    }

    private fun score(line: String): Int {
        val stack = ArrayDeque<ValidPair>()

        for (c in line.toCharArray()) {
            val maybeOpenedPair = matchOpeningChar(c)
            if (maybeOpenedPair != null) {
                stack.push(maybeOpenedPair)
            } else {
                val maybeClosedPair = matchClosingChar(c)
                if (maybeClosedPair != null) {
                    if (stack.isEmpty() || stack.peek() != maybeClosedPair) {
                        return maybeClosedPair.points
                    } else {
                        stack.pop()
                    }
                } else {
                    throw IllegalStateException("Unknown character: $c")
                }
            }
        }

        return 0
    }

    private fun matchOpeningChar(c: Char): ValidPair? {
        return validPairs.firstOrNull { pair -> pair.openingChar == c }
    }

    private fun matchClosingChar(c: Char): ValidPair? {
        return validPairs.firstOrNull { pair -> pair.closingChar == c }
    }

    data class ValidPair(val openingChar: Char, val closingChar: Char, val points: Int)
}