package day06

import Common

fun main() {
    val input = Common.readInput("/day06/input.txt")
    val result = Lanternfish1().go(input)
    println(result)
}

class Lanternfish1 {

    fun go(input: List<String>): Int {
        val fishList = ArrayList(input.first().split(',').map(String::toInt))

        repeat(80) {
            val size = fishList.size
            for (index in 0 until size) {
                val fish = fishList[index]
                if (fish > 0) {
                    fishList[index] = fish - 1
                } else {
                    fishList[index] = 6
                    fishList.add(8)
                }
            }
        }

        return fishList.size
    }
}