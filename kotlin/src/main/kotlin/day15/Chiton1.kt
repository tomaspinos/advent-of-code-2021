package day15

import Common
import kotlin.math.max
import kotlin.math.min

fun main() {
    println(Chiton1().lowestTotalRiskPath(Common.readInput("/day15/input.txt")))
}

class Chiton1 {

    fun lowestTotalRiskPath(input: List<String>): Int {
        val caveMap = readInput(input)

        val costMap = Array(caveMap.mapWidth) { IntArray(caveMap.mapHeight) { Integer.MAX_VALUE } }
        costMap[0][0] = 0

        var links = caveMap.links()
        var startingLinks = links.getStartingLinks()

        while (costMap[caveMap.mapWidth - 1][caveMap.mapHeight - 1] == Integer.MAX_VALUE) {
            val cheapestLinks = startingLinks.getCheapestLinks(costMap)
            cheapestLinks.links.forEach { cheapestLink ->
                costMap[cheapestLink.to.x][cheapestLink.to.y] = costMap[cheapestLink.from.x][cheapestLink.from.y] + caveMap.map[cheapestLink.to.x][cheapestLink.to.y]
                links = links.removeLinksLeadingTo(cheapestLink.to)
            }
            startingLinks = links.getStartingLinks()
        }

        return costMap[caveMap.mapWidth - 1][caveMap.mapHeight - 1]
    }

    private fun readInput(input: List<String>): CaveMap {
        val map =
            input.map { line -> line.toCharArray().map { char -> Integer.parseInt(char.toString()) }.toIntArray() }
                .toTypedArray()
        return CaveMap(map)
    }

    data class Coordinates(val x: Int, val y: Int)

    data class Link(val from: Coordinates, val to: Coordinates, val cost: Int)

    data class Links(val links: List<Link>) {

        fun isEmpty(): Boolean {
            return links.isEmpty()
        }

        fun getStartingLinks(): Links {
            val reachableCells = links.map { link -> link.to }.toSet()
            return Links(links.filter { link -> !reachableCells.contains(link.from) })
        }

        fun getCheapestLinks(costMap: Array<IntArray>): Links {
            return Links(links
                .groupBy { link -> link.to }
                .mapValues { entry ->
                    val linksToTheSameCell = entry.value
                    linksToTheSameCell.minByOrNull { link -> costMap[link.from.x][link.from.y] }!!
                }
                .values
                .toList())
        }

        fun removeLinksLeadingTo(cell: Coordinates): Links {
            return Links(links.filter { link -> link.to != cell })
        }
    }

    class CaveMap(val map: Array<IntArray>) {

        val mapWidth: Int = map.size

        val mapHeight: Int = map[0].size

        fun links(): Links {
            val links = ArrayList<Link>()
            for (x in map.indices) {
                for (y in map[x].indices) {
                    val cell = Coordinates(x, y)
                    links.addAll(getNeighboringCells(cell).map { neighboringCell ->
                        Link(cell, neighboringCell, map[neighboringCell.x][neighboringCell.y])
                    })
                }
            }
            return Links(links)
                .removeLinksLeadingTo(Coordinates(0, 0))
        }

        private fun getNeighboringCells(cell: Coordinates): List<Coordinates> {
            return listOf(
                Coordinates(cell.x, max(cell.y - 1, 0)),
                Coordinates(max(cell.x - 1, 0), cell.y),
                Coordinates(min(cell.x + 1, mapWidth - 1), cell.y),
                Coordinates(cell.x, min(cell.y + 1, mapHeight - 1))
            ).distinct()
        }
    }
}