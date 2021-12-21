package day21

import kotlin.math.max

fun main() {
    println(DiracDice2().go(2, 8))
}

class DiracDice2 {

    private val winnerCache: MutableMap<CacheKey, Pair<Long, Long>> = HashMap()

    fun go(startingPosition1: Int, startingPosition2: Int): Long {
        val wins = go(startingPosition1 - 1, startingPosition2 - 1, 0, 0, 0, Status.p11)
        return max(wins.first, wins.second)
    }

    fun go(position1: Int, position2: Int, score1: Int, score2: Int, diceSum: Int, status: Status): Pair<Long, Long> {
        val cacheKey = CacheKey(position1, position2, score1, score2, diceSum, status)
        val wins = winnerCache[cacheKey]
        if (wins != null) {
            return wins
        }

        val result: Pair<Long, Long> = when (status) {
            Status.p11, Status.p12, Status.p13, Status.p21, Status.p22, Status.p23 -> {
                val wins1 = go(position1, position2, score1, score2, diceSum + 1, status.next())
                val wins2 = go(position1, position2, score1, score2, diceSum + 2, status.next())
                val wins3 = go(position1, position2, score1, score2, diceSum + 3, status.next())
                Pair(wins1.first + wins2.first + wins3.first, wins1.second + wins2.second + wins3.second)
            }
            Status.p1move -> {
                val newPosition1 = (position1 + diceSum) % 10
                val newScore1 = score1 + newPosition1 + 1
                if (newScore1 >= 21) {
                    Pair(1, 0)
                } else {
                    go(newPosition1, position2, newScore1, score2, 0, status.next())
                }
            }
            Status.p2move -> {
                val newPosition2 = (position2 + diceSum) % 10
                val newScore2 = score2 + newPosition2 + 1
                if (newScore2 >= 21) {
                    Pair(0, 1)
                } else {
                    go(position1, newPosition2, score1, newScore2, 0, status.next())
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

    data class CacheKey(val position1: Int, val position2: Int, val score1: Int, val score2: Int, val diceSum: Int, val status: Status)
}