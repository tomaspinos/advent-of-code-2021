package day1

import java.io.File

fun main() {
    println(SonarSweep1().countIncreases())
}

class SonarSweep1 {

    fun countIncreases(): Int {
        val values = readValues()

        var increases = 0

        for (i in 1 until values.size) {
            if (values[i] > values[i - 1]) {
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
