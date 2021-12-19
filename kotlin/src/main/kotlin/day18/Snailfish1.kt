package day18

import Common

fun main() {
    println(Snailfish1().magnitude(Common.readInput("/day18/input.txt")))
}

class Snailfish1 {

    fun magnitude(input: List<String>): Long {
        return add(SnailfishNumberReader().read(input)).magnitude()
    }

    fun add(numbers: List<SnailfishNumber>): SnailfishNumber {
        var result = numbers.first()
        for (number in numbers.subList(1, numbers.size)) {
            result = add(result, number)
        }
        return result
    }

    fun add(a: SnailfishNumber, b: SnailfishNumber): SnailfishNumberPair {
        val pair = SnailfishNumberPair(a, b)
        pair.reduce()
        return pair
    }
}