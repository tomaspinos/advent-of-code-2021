package day18

data class SnailfishNumberRegular(var value: Long) : SnailfishNumber {

    override var parent: SnailfishNumberPair? = null

    override fun findNestedPair(depth: Int): SnailfishNumberPair? {
        return null
    }

    override fun findLargeRegular(): SnailfishNumberRegular? {
        return if (value >= 10) {
            this
        } else {
            null
        }
    }

    override fun findLeftmostRegular(): SnailfishNumberRegular {
        return this
    }

    override fun findRightmostRegular(): SnailfishNumberRegular {
        return this
    }

    fun add(otherValue: Long) {
        value += otherValue
    }

    fun split() {
        val pair =
            SnailfishNumberPair(SnailfishNumberRegular(value / 2), SnailfishNumberRegular((value / 2) + (value % 2)))
        parent!!.replace(this, pair)
    }

    override fun magnitude(): Long {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}