package day18

import Common

fun main() {
    println(Snailfish2().magnitude(Common.readInput("/day18/input.txt")))
}

class Snailfish2 {

    fun magnitude(input: List<String>): Long {
        val reader = SnailfishNumberReader()
        val numbers = reader.read(input)
        var maxMagnitude = Long.MIN_VALUE
        for (a in numbers) {
            for (b in numbers) {
                if (a !== b) {
                    val aa = reader.read(a.toString())
                    val bb = reader.read(b.toString())
                    val magnitude = add(aa, bb).magnitude()
                    if (magnitude > maxMagnitude) {
                        maxMagnitude = magnitude
                    }
                }
            }
        }
        return maxMagnitude
    }

    fun add(a: SnailfishNumber, b: SnailfishNumber): SnailfishNumberPair {
        val pair = SnailfishNumberPair(a, b)
        pair.reduce()
        return pair
    }
}