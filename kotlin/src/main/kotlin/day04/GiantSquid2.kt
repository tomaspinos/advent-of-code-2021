package day04

import Common

fun main() {
    val input = Common.readInput("/day04/input.txt")
    val result = GiantSquid2().bingo(input)
    println(result)
}

class GiantSquid2 {

    fun bingo(input: List<String>): Int {
        val numbers = input[0].split(',').map(String::toInt)

        val boards = readBoards(input.subList(1, input.size))

        for ((round, number) in numbers.withIndex()) {
            boards.play(round, number)
        }

        val lastWinningBoard = boards.getLastWinningBoard()
        if (lastWinningBoard != null) {
            return lastWinningBoard.sumAllUnmarkedNumbers() * lastWinningBoard.winningNumber!!
        } else {
            throw IllegalStateException()
        }
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

        fun play(round: Int, number: Int) {
            boards.forEach { board -> board.play(round, number) }
        }

        fun getLastWinningBoard(): Board? {
            return boards.maxByOrNull { it.winningRound }!!
        }
    }

    class Board(numbers: List<List<Int>>) {

        private val rows: List<List<Cell>>

        var winningRound: Int = -1

        var winningNumber: Int? = null

        init {
            rows = numbers.map { row -> row.map { number -> Cell(number, false) } }
        }

        fun play(round: Int, number: Int) {
            if (winningRound >= 0) return

            rows.forEach { row ->
                row.forEach { cell ->
                    if (cell.number == number) {
                        cell.checked = true
                    }
                }
            }

            if (isWinning()) {
                winningRound = round
                winningNumber = number
            }
        }

        fun sumAllUnmarkedNumbers(): Int {
            return rows.sumOf { row -> row.filter { cell -> !cell.checked }.sumOf { cell -> cell.number } }
        }

        private fun isWinning(): Boolean {
            (0..4).forEach { index ->
                if (isWinningRow(index) || isWinningColumn(index)) {
                    return true
                }
            }
            return false
        }

        private fun isWinningRow(index: Int): Boolean {
            return rows[index].all { cell -> cell.checked }
        }

        private fun isWinningColumn(index: Int): Boolean {
            return rows.map { row -> row[index] }.all { cell -> cell.checked }
        }
    }

    data class Cell(val number: Int, var checked: Boolean)
}