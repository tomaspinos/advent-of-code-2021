package day25

fun main() {
    println(SeaCucumber1().go(Common.readInput("/day25/input.txt")))
}

class SeaCucumber1 {

    fun go(input: List<String>): Int {
        val sea = readInput(input)

        var steps = 0
        var anyMove = true

        while (anyMove) {
            steps++
            anyMove = sea.step()
        }

        return steps
    }

    private fun readInput(input: List<String>): Sea {
        val cells: List<List<Cell>> = input.map { line ->
            line.toCharArray()
                .map { char ->
                    when (char) {
                        '>' -> CucumberEast()
                        'v' -> CucumberSouth()
                        else -> Void()
                    }
                }
        }

        return Sea(cells.map { row -> row.toTypedArray() }.toTypedArray())
    }

    data class Coordinates(val x: Int, val y: Int)

    class Sea(private val cells: Array<Array<Cell>>) {

        private val height = cells.size

        private val width = cells[0].size

        fun step(): Boolean {
            val cellsWithCoordinates = ArrayList<Pair<Cell, Coordinates>>()
            for (y in cells.indices) {
                for (x in cells[y].indices) {
                    val cell = cells[y][x]
                    cellsWithCoordinates.add(Pair(cell, Coordinates(x, y)))
                }
            }

            val waves = cellsWithCoordinates.groupBy { (cell, _) -> cell.wave() }

            var anyCellMoved = false

            for (wave in (1..2)) {
                waves[wave]!!
                    .map { (cell, coordinates) -> cell.move(coordinates, this) }
                    .forEach { move -> anyCellMoved = anyCellMoved or move.move(this) }
            }

            return anyCellMoved
        }

        fun move(from: Coordinates, to: Coordinates) {
            if (isVoid(from)) {
                throw IllegalStateException("Unexpected void")
            }
            if (!isVoid(to)) {
                throw IllegalStateException("Unexpected cucumber")
            }
            cells[to.y % height][to.x % width] = cells[from.y % height][from.x % width]
            cells[from.y % height][from.x % width] = Void()
        }

        fun isVoid(coordinates: Coordinates): Boolean {
            return cells[coordinates.y % height][coordinates.x % width] is Void
        }

        fun print() {
            for (row in cells) {
                println(row.joinToString("") { cell -> cell.toString() })
            }
        }
    }

    abstract class Cell {

        abstract fun wave(): Int

        abstract fun move(coordinates: Coordinates, sea: Sea): Move
    }

    class Void : Cell() {

        override fun wave(): Int {
            return 0
        }

        override fun move(coordinates: Coordinates, sea: Sea): Move {
            return NoMove()
        }

        override fun toString(): String {
            return "."
        }
    }

    class CucumberEast : Cell() {

        override fun wave(): Int {
            return 1
        }

        override fun move(coordinates: Coordinates, sea: Sea): Move {
            val newCoordinates = Coordinates(coordinates.x + 1, coordinates.y)
            return if (sea.isVoid(newCoordinates)) {
                ActualMove(coordinates, newCoordinates)
            } else {
                NoMove()
            }
        }

        override fun toString(): String {
            return ">"
        }
    }

    class CucumberSouth : Cell() {

        override fun wave(): Int {
            return 2
        }

        override fun move(coordinates: Coordinates, sea: Sea): Move {
            val newCoordinates = Coordinates(coordinates.x, coordinates.y + 1)
            return if (sea.isVoid(newCoordinates)) {
                ActualMove(coordinates, newCoordinates)
            } else {
                NoMove()
            }
        }

        override fun toString(): String {
            return "v"
        }
    }

    abstract class Move {

        abstract fun move(sea: Sea): Boolean
    }

    class NoMove : Move() {

        override fun move(sea: Sea): Boolean {
            return false
        }
    }

    class ActualMove(private val from: Coordinates, private val to: Coordinates) : Move() {

        override fun move(sea: Sea): Boolean {
            sea.move(from, to)
            return true
        }
    }
}