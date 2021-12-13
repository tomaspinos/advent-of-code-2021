package day13

import Common

fun main() {
    TransparentOrigami2().fold(Common.readInput("/day13/input.txt"))
}

/**
 * EPUELPBR
 */
class TransparentOrigami2 {

    fun fold(input: List<String>) {
        val paperAndFolds = readInput(input)

        var paper = paperAndFolds.paper
        paperAndFolds.folds.forEach { fold ->
            paper = paper.fold(fold)
        }

        paper.print()
    }

    private fun readInput(input: List<String>): PaperAndFolds {
        val dots = ArrayList<Pair<Int, Int>>()
        val folds = ArrayList<Fold>()

        val iterator = input.iterator()
        var line = iterator.next()
        while (line.isNotBlank()) {
            val split = line.split(',')
            dots.add(Pair(split[0].toInt(), split[1].toInt()))
            line = iterator.next()
        }

        while (iterator.hasNext()) {
            line = iterator.next()
            val split = line.split(' ')[2].split('=')
            folds.add(Fold(if (split[0] == "x") FoldType.X else FoldType.Y, split[1].toInt()))
        }

        val xSize = 1 + dots.maxOf { dot -> dot.first }
        val ySize = 1 + dots.maxOf { dot -> dot.second }
        val dotArray = Array(xSize) { BooleanArray(ySize) }
        dots.forEach { dot -> dotArray[dot.first][dot.second] = true }

        return PaperAndFolds(Paper(dotArray), folds)
    }

    class Paper(private val dots: Array<BooleanArray>) {

        fun fold(fold: Fold): Paper {
            return when (fold.type) {
                FoldType.X -> foldAlongX(fold.axis)
                FoldType.Y -> foldAlongY(fold.axis)
            }
        }

        fun print() {
            for (y in dots[0].indices) {
                for (x in dots.indices) {
                    print(if (dots[x][y]) "#" else ".")
                }
                println()
            }
            println()
        }

        private fun foldAlongX(xAxis: Int): Paper {
            val newDots = Array(xAxis) { BooleanArray(dots[0].size) }

            for (x in newDots.indices) {
                for (y in newDots[x].indices) {
                    newDots[x][y] = dots[x][y]
                }
            }

            var leftX = xAxis - 1
            for (rightX in xAxis + 1 until dots.size) {
                for (y in dots[leftX].indices) {
                    val leftDot = dots[leftX][y]
                    val rightDot = dots[rightX][y]
                    val newDot = leftDot || rightDot
                    newDots[leftX][y] = newDot
                }
                leftX--
            }

            return Paper(newDots)
        }

        private fun foldAlongY(yAxis: Int): Paper {
            val newDots = Array(dots.size) { BooleanArray(yAxis) }

            for (x in newDots.indices) {
                for (y in newDots[x].indices) {
                    newDots[x][y] = dots[x][y]
                }
            }

            for (x in dots.indices) {
                var upperY = yAxis - 1
                for (lowerY in yAxis + 1 until dots[x].size) {
                    val upperDot = dots[x][upperY]
                    val lowerDot = dots[x][lowerY]
                    newDots[x][upperY] = upperDot || lowerDot
                    upperY--
                }
            }

            return Paper(newDots)
        }
    }

    enum class FoldType {

        X, Y
    }

    data class Fold(val type: FoldType, val axis: Int)

    data class PaperAndFolds(val paper: Paper, val folds: List<Fold>)
}