package day21

import kotlin.math.max

fun main() {
    println(DiracDice2().go(2, 8))
}

class DiracDice2 {

    private val winnerCache: MutableMap<CacheKey, Pair<Long, Long>> = HashMap()

    fun go(startingPosition1: Int, startingPosition2: Int): Long {
        val (first, second) = go(Pair(startingPosition1 - 1, startingPosition2 - 1), Pair(0, 0), 0, Status.p11)
        return max(first, second)
    }

    fun go(positions: Pair<Int, Int>, scores: Pair<Int, Int>, diceSum: Int, status: Status): Pair<Long, Long> {
        val cacheKey = CacheKey(positions, scores, diceSum, status)
        val wins = winnerCache[cacheKey]
        if (wins != null) {
            return wins
        }

        val result: Pair<Long, Long> = when (status) {
            Status.p11, Status.p12, Status.p13, Status.p21, Status.p22, Status.p23 -> {
                val wins1 = go(positions, scores, diceSum + 1, status.next())
                val wins2 = go(positions, scores, diceSum + 2, status.next())
                val wins3 = go(positions, scores, diceSum + 3, status.next())
                Pair(wins1.first + wins2.first + wins3.first, wins1.second + wins2.second + wins3.second)
            }
            Status.p1move -> {
                val newPosition1 = (positions.first + diceSum) % 10
                val newScore1 = scores.first + newPosition1 + 1
                if (newScore1 >= 21) {
                    Pair(1, 0)
                } else {
                    go(Pair(newPosition1, positions.second), Pair(newScore1, scores.second), 0, status.next())
                }
            }
            Status.p2move -> {
                val newPosition2 = (positions.second + diceSum) % 10
                val newScore2 = scores.second + newPosition2 + 1
                if (newScore2 >= 21) {
                    Pair(0, 1)
                } else {
                    go(Pair(positions.first, newPosition2), Pair(scores.first, newScore2), 0, status.next())
                }
            }
        }

        winnerCache[cacheKey] = result
        return result
    }

    enum class Status {

        p11, p12, p13, p1move,
        p21, p22, p23, p2move;

        fun next(): Status {
            return when (this) {
                p11 -> p12
                p12 -> p13
                p13 -> p1move
                p1move -> p21
                p21 -> p22
                p22 -> p23
                p23 -> p2move
                p2move -> p11
            }
        }
    }

    data class CacheKey(val positions: Pair<Int, Int>, val scores: Pair<Int, Int>, val diceSum: Int, val status: Status)
}