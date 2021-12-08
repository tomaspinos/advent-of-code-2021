package day08

import Common

fun main() {
    val input = Common.readInput("/day08/input.txt")
    val result = Segment2().go(input)
    println(result)
}

class Segment2 {

    private val digits: Array<Set<Char>> = arrayOf(
        "abcefg".toCharArray().toSet(),
        "cf".toCharArray().toSet(),
        "acdeg".toCharArray().toSet(),
        "acdfg".toCharArray().toSet(),
        "bcdf".toCharArray().toSet(),
        "abdfg".toCharArray().toSet(),
        "abdefg".toCharArray().toSet(),
        "acf".toCharArray().toSet(),
        "abcdefg".toCharArray().toSet(),
        "abcdfg".toCharArray().toSet()
    )

    fun go(input: List<String>): Int {
        return input.sumOf { line ->
            val split = line.split('|')
            val patterns = split[0].trim().split(' ')
            val resultDigits = split[1].trim().split(' ')
            decode(patterns, resultDigits)
        }
    }

    fun decode(patterns: List<String>, resultDigits: List<String>): Int {
        val patternOne = patterns.first { pattern -> pattern.length == 2 }
        val patternSeven = patterns.first { pattern -> pattern.length == 3 }
        val patternFour = patterns.first { pattern -> pattern.length == 4 }

        val segmentOccurences = patterns.flatMap { pattern -> pattern.toCharArray().toList() }
            .groupBy { char -> char }
            .mapValues { entry -> entry.value.size }

        // f = 9 occurences
        val f = segmentOccurences.filter { entry -> entry.value == 9 }.map { entry -> entry.key }.first()
        // b = 6 occurences
        val b = segmentOccurences.filter { entry -> entry.value == 6 }.map { entry -> entry.key }.first()
        // e = 4 occurences
        val e = segmentOccurences.filter { entry -> entry.value == 4 }.map { entry -> entry.key }.first()
        // a = 7 - 1
        val a = patternSeven.toCharArray().first { char -> !patternOne.contains(char) }
        // c = 8 occurences from remaining segments
        val c = segmentOccurences.filter { entry -> !setOf(f, b, e, a).contains(entry.key) && entry.value == 8 }.map { entry -> entry.key }.first()
        // d = 4 - (b c f)
        val d = patternFour.toCharArray().first { char -> !setOf(b, c, f).contains(char) }
        // g = Last segment
        val g = segmentOccurences.filter { entry -> !setOf(f, b, e, a, c, d).contains(entry.key) }.map { entry -> entry.key }.first()

        val segmentMapping = HashMap<Char, Char>()
        segmentMapping[a] = 'a'
        segmentMapping[b] = 'b'
        segmentMapping[c] = 'c'
        segmentMapping[d] = 'd'
        segmentMapping[e] = 'e'
        segmentMapping[f] = 'f'
        segmentMapping[g] = 'g'

        return resultDigits.map { pattern -> pattern.toCharArray().map { char -> segmentMapping[char] }.toSet() }
            .map { pattern -> digits.indexOf(pattern) }
            .joinToString("") { digit -> digit.toString() }
            .toInt()
    }
}