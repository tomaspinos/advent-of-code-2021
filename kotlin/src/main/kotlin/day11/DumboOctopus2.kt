package day11

import Common
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = Common.readInput("/day11/input.txt")
    val result = DumboOctopus2().flash(input)
    println(result)
}

class DumboOctopus2 {

    fun flash(input: List<String>): Int {
        val map =
            input.map { line -> line.toCharArray().map { char -> Integer.parseInt(char.toString()) }.toIntArray() }
                .toTypedArray()

        val energyLevelMap = EnergyLevelMap(map)

        var step = 0
        while (!energyLevelMap.allFlashed()) {
            energyLevelMap.step()
            step++
        }

        return step
    }

    class EnergyLevelMap(val map: Array<IntArray>) {

        var flashCount = 0

        fun step() {
            for (x in map.indices) {
                for (y in map[x].indices) {
                    map[x][y]++
                }
            }

            for (x in map.indices) {
                for (y in map[x].indices) {
                    if (map[x][y] > 9) {
                        flash(x, y)
                    }
                }
            }
        }

        fun allFlashed(): Boolean {
            var sum = 0
            for (x in map.indices) {
                for (y in map[x].indices) {
                    sum += map[x][y]
                }
            }
            return sum == 0
        }

        private fun flash(x: Int, y: Int) {
            flashCount++
            map[x][y] = 0
            for (xx in max(x - 1, 0)..min(x + 1, map.size - 1)) {
                for (yy in max(y - 1, 0)..min(y + 1, map[x].size - 1)) {
                    if (map[xx][yy] != 0) {
                        map[xx][yy]++
                        if (map[xx][yy] > 9) {
                            flash(xx, yy)
                        }
                    }
                }
            }
        }
    }
}