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
        val patternOne = patterns.first { it.length == 2 }
        val patternSeven = patterns.first { it.length == 3 }
        val patternFour = patterns.first { it.length == 4 }

        val segmentOccurences = patterns.flatMap { it.toCharArray().toList() }
            .groupBy { it }
            .mapValues { it.value.size }

        // f = 9 occurences
        val f = segmentOccurences.filter { it.value == 9 }.map { it.key }.first()
        // b = 6 occurences
        val b = segmentOccurences.filter { it.value == 6 }.map { it.key }.first()
        // e = 4 occurences
        val e = segmentOccurences.filter { it.value == 4 }.map { it.key }.first()
        // a = 7 - 1
        val a = patternSeven.toCharArray().first { !patternOne.contains(it) }
        // c = 8 occurences from remaining segments
        val c = segmentOccurences.filter { !setOf(f, b, e, a).contains(it.key) && it.value == 8 }.map { it.key }.first()
        // d = 4 - (b c f)
        val d = patternFour.toCharArray().first { !setOf(b, c, f).contains(it) }
        // g = Last segment
        val g = segmentOccurences.filter { !setOf(f, b, e, a, c, d).contains(it.key) }.map { it.key }.first()

        val segmentMapping = HashMap<Char, Char>()
        segmentMapping[a] = 'a'
        segmentMapping[b] = 'b'
        segmentMapping[c] = 'c'
        segmentMapping[d] = 'd'
        segmentMapping[e] = 'e'
        segmentMapping[f] = 'f'
        segmentMapping[g] = 'g'

        return resultDigits.map { pattern -> pattern.toCharArray().map { segmentMapping[it] }.toSet() }
            .map(digits::indexOf)
            .joinToString("", transform = Int::toString)
            .toInt()
    }
}