package day17

import kotlin.math.max

fun main() {
    println(TrickShot1().go(TrickShot1.Area(TrickShot1.Point(209, -59), TrickShot1.Point(238, -86))))
}

class TrickShot1 {

    fun go(targetArea: Area): Int {
        println("Target area: $targetArea")

        val maxX = targetArea.bottomRight.x
        var maxY = Int.MIN_VALUE

        for (x in 1..maxX) {
            for (y in 0..100) {
                maxY = max(maxY, simulateProbe(Point(x, y), targetArea) ?: Int.MIN_VALUE)
            }
        }

        return maxY
    }

    fun simulateProbe(velocity: Point, targetArea: Area): Int? {
        var currentVelocity = velocity
        var currentPoint = Point(0, 0)

        var maxY = Int.MIN_VALUE

        while (true) {
            currentPoint = currentPoint.plus(currentVelocity)
            maxY = max(maxY, currentPoint.y)

            if (targetArea.contains(currentPoint)) {
                val result = if (maxY > Int.MIN_VALUE) maxY else null
                println("$velocity -> $result")
                return result
            } else if (targetArea.cannotHit(currentPoint)) {
                return null
            }

            currentVelocity = adjustVelocity(currentVelocity)
        }
    }

    fun adjustVelocity(velocity: Point): Point {
        return Point(max(velocity.x - 1, 0), velocity.y - 1)
    }

    data class Point(val x: Int, val y: Int) {

        fun plus(other: Point): Point {
            return Point(x + other.x, y + other.y)
        }
    }

    data class Area(val topLeft: Point, val bottomRight: Point) {

        fun contains(point: Point): Boolean {
            return point.x in (topLeft.x..bottomRight.x) && point.y in (bottomRight.y..topLeft.y)
        }

        fun cannotHit(point: Point): Boolean {
            return point.x > bottomRight.x || point.y < bottomRight.y
        }
    }
}