package day10

import Common
import java.util.*

fun main() {
    val input = Common.readInput("/day10/input.txt")
    val result = SyntaxScoring2().score(input)
    println(result)
}

class SyntaxScoring2 {

    private val validPairs: List<ValidPair> = listOf(
        ValidPair('(', ')', 1),
        ValidPair('[', ']', 2),
        ValidPair('{', '}', 3),
        ValidPair('<', '>', 4)
    )

    fun score(input: List<String>): Long {
        val sortedScores = input
            .mapNotNull { line -> findIncompleteLine(line) }
            .map { stack -> autoCompleteLineAndScore(stack) }
            .sorted()
        return sortedScores[sortedScores.size / 2]
    }

    private fun findIncompleteLine(line: String): ArrayDeque<ValidPair>? {
        val stack = ArrayDeque<ValidPair>()

        for (c in line.toCharArray()) {
            val maybeOpenedPair = matchOpeningChar(c)
            if (maybeOpenedPair != null) {
                stack.push(maybeOpenedPair)
            } else {
                val maybeClosedPair = matchClosingChar(c)
                if (maybeClosedPair != null) {
                    if (stack.isEmpty() || stack.peek() != maybeClosedPair) {
                        return null
                    } else {
                        stack.pop()
                    }
                } else {
                    throw IllegalStateException("Unknown character: $c")
                }
            }
        }

        return if (stack.isEmpty()) {
            null
        } else {
            stack
        }
    }

    private fun autoCompleteLineAndScore(stack: ArrayDeque<ValidPair>): Long {
        var score = 0L
        while (!stack.isEmpty()) {
            score = (score * 5) + stack.pop().points
        }
        return score
    }

    private fun matchOpeningChar(c: Char): ValidPair? {
        return validPairs.firstOrNull { pair -> pair.openingChar == c }
    }

    private fun matchClosingChar(c: Char): ValidPair? {
        return validPairs.firstOrNull { pair -> pair.closingChar == c }
    }

    data class ValidPair(val openingChar: Char, val closingChar: Char, val points: Int)
}