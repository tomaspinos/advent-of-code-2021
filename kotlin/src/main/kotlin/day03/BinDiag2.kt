package day03

import Common

fun main() {
    val input = Common.readInput("/day03/input.txt")
    val result = BinDiag2().diag(input)
    println(result)
    println(result.oxygenGeneratorRating * result.co2ScrubberRating)
}

class BinDiag2 {

    fun diag(input: List<String>): Result {
        val bitCount = input[0].length

        val oxygenGeneratorRatings = ArrayList(input)
        for (bit in 0 until bitCount) {
            if (oxygenGeneratorRatings.size > 1) {
                val mostCommonBitValue = getMostCommonBitValue(bit, oxygenGeneratorRatings)
                oxygenGeneratorRatings.removeIf { number -> number[bit] != mostCommonBitValue }
            }
        }

        val co2ScrubbingRatings = ArrayList(input)
        for (bit in 0 until bitCount) {
            if (co2ScrubbingRatings.size > 1) {
                val mostCommonBitValue = getMostCommonBitValue(bit, co2ScrubbingRatings)
                co2ScrubbingRatings.removeIf { number -> number[bit] == mostCommonBitValue }
            }
        }

        val oxygenGeneratorRating = Integer.parseInt(oxygenGeneratorRatings[0], 2)
        val co2ScrubberRating = Integer.parseInt(co2ScrubbingRatings[0], 2)

        return Result(oxygenGeneratorRating, co2ScrubberRating)
    }

    private fun getMostCommonBitValue(bit: Int, numbers: List<String>): Char {
        var nonZeroValueCount = 0

        for (number in numbers) {
            if (number[bit] == '1') {
                nonZeroValueCount++
            }
        }

        return if (nonZeroValueCount >= numbers.size - nonZeroValueCount) {
            '1'
        } else {
            '0'
        }
    }

    data class Result(val oxygenGeneratorRating: Int, val co2ScrubberRating: Int)
}