package day01

import Common

fun main() {
    val values = Common.readInput("/day01/input.txt").map(String::toInt)
    println(SonarSweep1().countIncreases(values))
}

class SonarSweep1 {

    fun countIncreases(values: List<Int>): Int {
        var increases = 0

        for (i in 1 until values.size) {
            if (values[i] > values[i - 1]) {
                increases++
            }
        }

        return increases
    }
}
