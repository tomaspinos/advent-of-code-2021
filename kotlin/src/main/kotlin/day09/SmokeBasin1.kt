package day09

import Common

fun main() {
    val input = Common.readInput("/day09/input.txt")
    val result = SmokeBasin1().riskLevel(input)
    println(result)
}

class SmokeBasin1 {

    fun riskLevel(input: List<String>): Int {
        val map =
            input.map { line -> line.toCharArray().map { char -> Integer.parseInt(char.toString()) }.toIntArray() }
                .toTypedArray()

        return HeightMap(map).getRiskLevelSum()
    }

    class HeightMap(val map: Array<IntArray>) {

        private val neighborCoordinates: List<Pair<Int, Int>> = listOf(
            Pair(0, -1),
            Pair(-1, 0), Pair(1, 0),
            Pair(0, 1),
        )

        fun getRiskLevelSum(): Int {
            return getLowPoints().sumOf { point -> point + 1 }
        }

        private fun getLowPoints(): List<Int> {
            val lowPoints = ArrayList<Int>()
            for (x in map.indices) {
                for (y in map[x].indices) {
                    if (getNeighbors(x, y).all { neighbor -> neighbor > map[x][y] }) {
                        lowPoints.add(map[x][y])
                    }
                }
            }
            return lowPoints
        }

        private fun getNeighbors(x: Int, y: Int): List<Int> {
            return neighborCoordinates.flatMap { coordinates ->
                getPoint(
                    x + coordinates.first,
                    y + coordinates.second
                )
            }
        }

        private fun getPoint(x: Int, y: Int): List<Int> {
            return if (x in map.indices && y in map[0].indices) {
                listOf(map[x][y])
            } else {
                emptyList()
            }
        }
    }
}