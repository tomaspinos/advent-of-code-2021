package day06

import Common

fun main() {
    val input = Common.readInput("/day06/input.txt")
    val result = Lanternfish2().go(input)
    println(result)
}

class Lanternfish2 {

    fun go(input: List<String>): Long {
        val fishList = ArrayList(input.first().split(',').map(String::toInt))

        val cache = HashMap<FishAndRemainingRounds, Long>()

        var count = 0L

        for (fish in fishList) {
            count += simulateFish(fish, 256, cache)
        }

        return count
    }

    private fun simulateFish(fish: Int, remainingRounds: Int, cache: HashMap<FishAndRemainingRounds, Long>): Long {
        if (remainingRounds == 0) {
            return 1
        }

        val maybeCount = cache[FishAndRemainingRounds(fish, remainingRounds)]
        if (maybeCount != null) {
            return maybeCount
        }

        return if (fish > 0) {
            val count = simulateFish(fish - 1, remainingRounds - 1, cache)
            cache[FishAndRemainingRounds(fish, remainingRounds)] = count
            count
        } else {
            val count1 = simulateFish(6, remainingRounds - 1, cache)
            cache[FishAndRemainingRounds(6, remainingRounds -1)] = count1
            val count2 = simulateFish(8, remainingRounds - 1, cache)
            cache[FishAndRemainingRounds(8, remainingRounds - 1)] = count2
            count1 + count2
        }
    }

    data class FishAndRemainingRounds(val fish: Int, val remainingRounds: Int)
}