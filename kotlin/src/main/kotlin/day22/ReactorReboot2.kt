package day22

import Common
import kotlin.math.min

fun main() {
    println(ReactorReboot2().go(Common.readInput("/day22/input.txt")))
}

class ReactorReboot2 {

    fun go(input: List<String>): Long {
        val cuboids = readInput(input).iterator()

        val processedCuboids = ArrayList<Cuboid>()

        while (cuboids.hasNext()) {
            val cuboid = cuboids.next()

            for (processedCuboid in ArrayList(processedCuboids)) {
                cuboid.intersection(processedCuboid, !processedCuboid.on)?.also { processedCuboids.add(it) }
            }

            if (cuboid.on) {
                processedCuboids.add(cuboid)
            }
        }

        return processedCuboids.sumOf { cuboid -> if (cuboid.on) cuboid.dimension() else -cuboid.dimension() }
    }

    fun readInput(input: List<String>): List<Cuboid> {
        return input.map(this::readStep)
    }

    fun readStep(line: String): Cuboid {
        // on x=-20..26,y=-36..17,z=-47..7
        val split = line.split(' ')
        val on = split[0] == "on"
        val ranges = split[1].split(',')
        val xRange = ranges[0].substring(2).split("..")
        val yRange = ranges[1].substring(2).split("..")
        val zRange = ranges[2].substring(2).split("..")
        return Cuboid(
            on,
            LongRange(xRange[0].toLong(), xRange[1].toLong()),
            LongRange(yRange[0].toLong(), yRange[1].toLong()),
            LongRange(zRange[0].toLong(), zRange[1].toLong())
        )
    }

    data class Cuboid(val on: Boolean, val xRange: LongRange, val yRange: LongRange, val zRange: LongRange) {

        fun dimension(): Long {
            return (xRange.last - xRange.first + 1) * (yRange.last - yRange.first + 1) * (zRange.last - zRange.first + 1)
        }

        fun intersection(other: Cuboid, intersectionOn: Boolean): Cuboid? {
            val intersection = Cuboid(
                intersectionOn,
                intersection(xRange, other.xRange),
                intersection(yRange, other.yRange),
                intersection(zRange, other.zRange)
            )
            return if (intersection.isValid()) intersection else null
        }

        private fun intersection(r1: LongRange, r2: LongRange): LongRange {
            val a: LongRange
            val b: LongRange

            if (r1.first < r2.first) {
                a = r1
                b = r2
            } else {
                a = r2
                b = r1
            }

            return if (a.last < b.first) {
                LongRange.EMPTY
            } else {
                val min = b.first
                val max = min(a.last, b.last)
                LongRange(min, max)
            }
        }

        private fun isValid(): Boolean {
            return xRange.last >= xRange.first && yRange.last >= yRange.first && zRange.last >= zRange.first
        }
    }
}