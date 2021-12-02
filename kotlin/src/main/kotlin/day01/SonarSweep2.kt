package day01

import Common

fun main() {
    val values = Common.readInput("/day01/input.txt").map(String::toInt)
    println(SonarSweep2().countIncreases(values))
}

class SonarSweep2 {

    fun countIncreases(values: List<Int>): Int {
        val values2 = values.subList(1, values.size)
        val values3 = values.subList(2, values.size)

        var increases = 0

        for (i in 1 until values3.size) {
            val window1 = values[i - 1] + values2[i - 1] + values3[i - 1]
            val window2 = values[i] + values2[i] + values3[i]

            if (window2 > window1) {
                increases++
            }
        }

        return increases
    }
}
