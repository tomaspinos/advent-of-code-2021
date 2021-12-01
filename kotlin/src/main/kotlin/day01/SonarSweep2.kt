package day01

import java.io.File

fun main() {
    println(SonarSweep2().countIncreases())
}

class SonarSweep2 {

    fun countIncreases(): Int {
        val values = readValues()
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

    private fun readValues(): List<Int> {
        return File(this::class.java.getResource("input.txt").path)
            .readLines()
            .map { line -> Integer.valueOf(line) }
    }
}
