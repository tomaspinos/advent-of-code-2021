package day05

import Common
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = Common.readInput("/day05/input.txt")
    val result = HydrothermalVenture2().measure(input)
    println(result)
}

class HydrothermalVenture2 {

    fun measure(input: List<String>): Int {
        val lines = readLines(input)

        val maxX = lines.flatMap { line -> listOf(line.from.x, line.to.x) }.maxOf { x -> x }
        val maxY = lines.flatMap { line -> listOf(line.from.y, line.to.y) }.maxOf { y -> y }

        val diagram = Diagram(maxX + 1, maxY + 1)
        lines.forEach { line -> diagram.markPoints(line.getAllPoints()) }
        return diagram.countPointsWhereAtLeastTwoLinesOverlap()
    }

    private fun readLines(input: List<String>): List<Line> {
        return input.map { s ->
            val points = s.split(" -> ")
            val from = points[0].split(',')
            val to = points[1].split(',')
            Line(Point(from[0].toInt(), from[1].toInt()), Point(to[0].toInt(), to[1].toInt()))
        }
    }

    class Diagram(val width: Int, val height: Int) {

        private val pointMarks: Array<IntArray> = Array(width) { IntArray(height) }

        fun markPoints(points: List<Point>) {
            points.forEach { point -> pointMarks[point.x][point.y]++ }
        }

        fun countPointsWhereAtLeastTwoLinesOverlap(): Int {
            var count = 0
            (0 until width).forEach { x ->
                (0 until height).forEach { y ->
                    if (pointMarks[x][y] > 1) {
                        count++
                    }
                }
            }
            return count
        }
    }

    data class Point(val x: Int, val y: Int)

    data class Line(val from: Point, val to: Point) {

        fun getAllPoints(): List<Point> {
            return if (from.x == to.x) {
                (min(from.y, to.y)..max(from.y, to.y)).map { y -> Point(from.x, y) }
            } else if (from.y == to.y) {
                (min(from.x, to.x)..max(from.x, to.x)).map { x -> Point(x, from.y) }
            } else {
                val points = ArrayList<Point>()

                points.add(from)

                var x = from.x
                var y = from.y
                val deltaX = if (from.x < to.x) 1 else -1
                val deltaY = if (from.y < to.y) 1 else -1
                while (x != to.x) {
                    x += deltaX
                    y += deltaY
                    points.add(Point(x, y))
                }

                return points
            }
        }
    }
}