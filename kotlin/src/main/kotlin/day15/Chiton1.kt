package day15

import Common
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * 390
 */
fun main() {
    println(Chiton1().lowestTotalRiskPath(Common.readInput("/day15/input.txt")))
}

class Chiton1 {

    fun lowestTotalRiskPath(input: List<String>): Int {
        val caveMap = readInput(input)

        val start = Coordinates(0, 0)
        val end = Coordinates(caveMap.mapWidth - 1, caveMap.mapHeight - 1)

        val cellQueue = PriorityQueue<Coordinates> { cell1, cell2 -> caveMap.getCost(cell1) - caveMap.getCost(cell2) }

        caveMap.setCost(start, 0)
        cellQueue.add(start)

        while (cellQueue.isNotEmpty()) {
            val currentCell = cellQueue.remove()
            caveMap.getNeighboringCells(currentCell).forEach { neighboringCell ->
                val cost = caveMap.getCost(currentCell) + caveMap.getOriginalCost(neighboringCell)
                if (cost < caveMap.getCost(neighboringCell)) {
                    caveMap.setCost(neighboringCell, cost)
                    cellQueue.add(neighboringCell)
                }
            }
        }

        return caveMap.getCost(end)
    }

    private fun readInput(input: List<String>): CaveMap {
        val map =
            input.map { line -> line.toCharArray().map { char -> Integer.parseInt(char.toString()) }.toIntArray() }
                .toTypedArray()
        return CaveMap(map)
    }

    data class Coordinates(val x: Int, val y: Int)

    class CaveMap(val map: Array<IntArray>) {

        val mapWidth: Int = map.size

        val mapHeight: Int = map[0].size

        private val costMap: Array<IntArray> = Array(mapWidth) { IntArray(mapHeight) { Integer.MAX_VALUE } }

        fun getOriginalCost(cell: Coordinates): Int {
            return map[cell.x][cell.y]
        }

        fun getCost(cell: Coordinates): Int {
            return costMap[cell.x][cell.y]
        }

        fun setCost(cell: Coordinates, cost: Int) {
            costMap[cell.x][cell.y] = cost
        }

        fun getNeighboringCells(cell: Coordinates): List<Coordinates> {
            return listOf(
                Coordinates(cell.x, max(cell.y - 1, 0)),
                Coordinates(max(cell.x - 1, 0), cell.y),
                Coordinates(min(cell.x + 1, mapWidth - 1), cell.y),
                Coordinates(cell.x, min(cell.y + 1, mapHeight - 1))
            ).distinct()
        }
    }
}