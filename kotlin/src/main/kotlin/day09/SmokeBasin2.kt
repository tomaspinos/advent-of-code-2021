package day09

import Common

fun main() {
    val input = Common.readInput("/day09/input.txt")
    val result = SmokeBasin2().riskLevel(input)
    println(result)
}

class SmokeBasin2 {

    fun riskLevel(input: List<String>): Int {
        val map =
            input.map { line -> line.toCharArray().map { char -> Integer.parseInt(char.toString()) }.toIntArray() }
                .toTypedArray()

        return HeightMap(map).getLargestBasinSizesMultiplication()
    }

    class HeightMap(val map: Array<IntArray>) {

        private val neighborCoordinates: List<Coordinates> = listOf(
            Coordinates(0, -1),
            Coordinates(-1, 0), Coordinates(1, 0),
            Coordinates(0, 1),
        )

        fun getLargestBasinSizesMultiplication(): Int {
            return getLowPoints()
                .asSequence()
                .map { lowPoint -> getBasin(lowPoint) }
                .map { basin -> basin.size }
                .sortedDescending()
                .take(3)
                .reduce { acc, i -> acc * i }
        }

        private fun getBasin(lowPoint: Coordinates): Set<Coordinates> {
            val basin = HashSet<Coordinates>()
            basin.add(lowPoint)
            getBasinR(lowPoint, basin)
            return basin
        }

        private fun getBasinR(basinPoint: Coordinates, basin: HashSet<Coordinates>) {
            getNeighbors(basinPoint).forEach { neighbor ->
                if (!basin.contains(neighbor) && value(neighbor) < 9 && value(neighbor) > value(basinPoint)) {
                    basin.add(neighbor)
                    getBasinR(neighbor, basin)
                }
            }
        }

        private fun getLowPoints(): List<Coordinates> {
            val lowPoints = ArrayList<Coordinates>()
            for (x in map.indices) {
                for (y in map[x].indices) {
                    val coordinates = Coordinates(x, y)
                    if (getNeighbors(coordinates).all { neighbor -> value(neighbor) > value(coordinates) }) {
                        lowPoints.add(coordinates)
                    }
                }
            }
            return lowPoints
        }

        private fun getNeighbors(coordinates: Coordinates): List<Coordinates> {
            return neighborCoordinates.flatMap { delta ->
                val neighbor = coordinates.move(delta)
                if (neighbor.x in map.indices && neighbor.y in map[0].indices) {
                    listOf(neighbor)
                } else {
                    emptyList()
                }
            }
        }

        private fun value(coordinates: Coordinates): Int {
            return map[coordinates.x][coordinates.y]
        }
    }

    data class Coordinates(val x: Int, val y: Int) {

        fun move(delta: Coordinates): Coordinates {
            return Coordinates(x + delta.x, y + delta.y)
        }
    }
}