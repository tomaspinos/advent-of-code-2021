package day22

import Common

fun main() {
    println(ReactorReboot1().go(Common.readInput("/day22/input.txt")))
}

class ReactorReboot1 {

    fun go(input: List<String>): Long {
        val steps = readInput(input)

        val reversedSteps = steps.reversed()

        var count = 0L

        for (x in -50..50) {
            for (y in -50..50) {
                for (z in -50..50) {
                    for (step in reversedSteps) {
                        if (step.contains(x, y, z)) {
                            if (step.on) {
                                count++
                            }
                            break
                        }
                    }
                }
            }
        }

        return count
    }

    fun readInput(input: List<String>): List<Step> {
        return input.map(this::readStep)
    }

    fun readStep(line: String): Step {
        // on x=-20..26,y=-36..17,z=-47..7
        val split = line.split(' ')
        val on = split[0] == "on"
        val ranges = split[1].split(',')
        val xRange = ranges[0].substring(2).split("..")
        val yRange = ranges[1].substring(2).split("..")
        val zRange = ranges[2].substring(2).split("..")
        return Step(
            on,
            IntRange(xRange[0].toInt(), xRange[1].toInt()),
            IntRange(yRange[0].toInt(), yRange[1].toInt()),
            IntRange(zRange[0].toInt(), zRange[1].toInt()))
    }

    data class Step(val on: Boolean, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

        fun contains(x: Int, y: Int, z: Int): Boolean {
            return x in xRange && y in yRange && z in zRange
        }
    }
}