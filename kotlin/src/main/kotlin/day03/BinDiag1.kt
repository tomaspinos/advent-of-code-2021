package day03

import Common

fun main() {
    val input = Common.readInput("/day03/input.txt")
    val result = BinDiag1().diag(input)
    println(result)
    println(result.gammaRate * result.epsilonRate)
}

class BinDiag1 {

    fun diag(input: List<String>): Result {
        val nonZeroBitCounts = IntArray(input[0].length)

        for (binaryNumber in input) {
            for ((i, bit) in binaryNumber.toCharArray().withIndex()) {
                if (bit == '1') {
                    nonZeroBitCounts[i]++
                }
            }
        }

        var gammaRateAsString = ""
        var epsilonRateAsString = ""
        for (nonZeroBitCount in nonZeroBitCounts) {
            if (nonZeroBitCount >= input.size / 2) {
                gammaRateAsString += "1"
                epsilonRateAsString += "0"
            } else {
                gammaRateAsString += "0"
                epsilonRateAsString += "1"
            }
        }

        val gammaRate = Integer.parseInt(gammaRateAsString, 2)
        val epsilonRate = Integer.parseInt(epsilonRateAsString, 2)

        return Result(gammaRate, epsilonRate)
    }

    data class Result(val gammaRate: Int, val epsilonRate: Int)
}