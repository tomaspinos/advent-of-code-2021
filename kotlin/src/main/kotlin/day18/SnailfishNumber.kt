package day18

interface SnailfishNumber {

    var parent: SnailfishNumberPair?

    fun findNestedPair(depth: Int): SnailfishNumberPair?

    fun findLargeRegular(): SnailfishNumberRegular?

    fun findLeftmostRegular(): SnailfishNumberRegular?

    fun findRightmostRegular(): SnailfishNumberRegular?

    fun magnitude(): Long
}