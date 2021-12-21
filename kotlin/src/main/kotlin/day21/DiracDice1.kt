package day21

fun main() {
    println(DiracDice1().go(2, 8))
}

class DiracDice1 {

    fun go(startingPosition1: Int, startingPosition2: Int): Int {
        var position1 = startingPosition1 - 1
        var position2 = startingPosition2 - 1
        var score1 = 0
        var score2 = 0
        val dice = Dice()

        while (true) {
            position1 = nextPosition(position1, dice)
            score1 += (position1 + 1)
            if (score1 >= 1000) {
                return score2 * dice.rolls
            }
            position2 = nextPosition(position2, dice)
            score2 += (position2 + 1)
            if (score2 >= 1000) {
                return score1 * dice.rolls
            }
        }
    }

    fun nextPosition(position: Int, dice: Dice): Int {
        return (position + dice.next3Sum()) % 10
    }

    data class Dice(var value: Int = 0, var rolls: Int = 0) {

        fun next(): Int {
            val result = value + 1
            value = (value + 1) % 100
            rolls++
            return result
        }

        fun next3Sum(): Int {
            return next() + next() + next()
        }
    }
}