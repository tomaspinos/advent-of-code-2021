package day04

import Common

fun main() {
    val input = Common.readInput("/day04/input.txt")
    val result = GiantSquid1().bingo(input)
    println(result)
}

class GiantSquid1 {

    fun bingo(input: List<String>): Int {
        val numbers = input[0].split(',').map(String::toInt)

        val boards = readBoards(input.subList(1, input.size))

        for (number in numbers) {
            val winningBoard = boards.play(number)
            if (winningBoard != null) {
                return winningBoard.sumAllUnmarkedNumbers() * number
            }
        }

        throw IllegalStateException("No winning board")
    }

    private fun readBoards(input: List<String>): Boards {
        val boardList = ArrayList<Board>()

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            iterator.next()
            val numbers = (1..5).map {
                val line = iterator.next()
                val rowNumbers = line.trim().split("  ", " ")
                rowNumbers.map(String::toInt)
            }
            boardList.add(Board(numbers))
        }

        return Boards(boardList)
    }

    class Boards(private val boards: List<Board>) {

        fun play(number: Int): Board? {
            boards.forEach { board -> board.check(number) }
            return boards.firstOrNull { board -> board.isWinning() }
        }
    }

    class Board(numbers: List<List<Int>>) {

        private val rows: List<List<Cell>>

        init {
            rows = numbers.map { row ->
                row.map { number -> Cell(number, false) }
            }
        }

        fun check(number: Int) {
            rows.forEach { row ->
                row.forEach { cell ->
                    if (cell.number == number) {
                        cell.checked = true
                    }
                }
            }
        }

        fun isWinning(): Boolean {
            (0..4).forEach { index ->
                if (isWinningRow(index) || isWinningColumn(index)) {
                    return true
                }
            }
            return false
        }

        fun sumAllUnmarkedNumbers(): Int {
            return rows.sumOf { row ->
                row.filter { cell -> !cell.checked }
                    .sumOf { cell -> cell.number }
            }
        }

        private fun isWinningRow(index: Int): Boolean {
            return rows[index].all { cell -> cell.checked }
        }

        private fun isWinningColumn(index: Int): Boolean {
            return rows.map { row -> row[index] }
                .all { cell -> cell.checked }
        }
    }

    data class Cell(val number: Int, var checked: Boolean)
}