package day17

import kotlin.math.max

fun main() {
    println(TrickShot2().go(TrickShot2.Area(TrickShot2.Point(209, -59), TrickShot2.Point(238, -86))))
}

class TrickShot2 {

    fun go(targetArea: Area): Int {
        println("Target area: $targetArea")

        val hits = HashSet<Point>()

        for (x in 1..targetArea.bottomRight.x) {
            for (y in -100..100) {
                val velocity = Point(x, y)
                if (simulateProbe(velocity, targetArea)) {
                    hits.add(velocity)
                }
            }
        }

        return hits.size
    }

    fun simulateProbe(velocity: Point, targetArea: Area): Boolean {
        var currentVelocity = velocity
        var currentPoint = Point(0, 0)

        while (true) {
            currentPoint = currentPoint.plus(currentVelocity)

            if (targetArea.contains(currentPoint)) {
                println(velocity)
                return true
            } else if (targetArea.cannotHit(currentPoint)) {
                return false
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