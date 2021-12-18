package day18

import Common

fun main() {
    println(Snailfish1().magnitude(Common.readInput("/day18/input.txt")))
}

class Snailfish1 {

    fun magnitude(input: List<String>): Long {
        return add(read(input)).magnitude()
    }

    fun add(numbers: List<SnailfishNumber>): SnailfishNumber {
        var result = numbers.first()
        for (number in numbers.subList(1, numbers.size)) {
            result = add(result, number)
        }
        return result
    }

    fun add(a: SnailfishNumber, b: SnailfishNumber): SnailfishNumber {
        val pair = Pair(a, b)
        reduce(pair)
        return pair
    }

    fun reduce(number: SnailfishNumber) {
        var continueReducing = true
        while (continueReducing) {
            continueReducing = false
            val pairToExplode = number.findNestedPair(1)
            if (pairToExplode != null) {
                pairToExplode.explode()
                continueReducing = true
            } else {
                val largeRegular = number.findLargeRegular()
                if (largeRegular != null) {
                    largeRegular.split()
                    continueReducing = true
                }
            }
        }
    }

    fun read(input: String): SnailfishNumber {
        return read(input.toCharArray().toMutableList())
    }

    private fun read(characters: MutableList<Char>): SnailfishNumber {
        val ch = characters.removeFirst()
        if (ch == '[') {
            val left = read(characters)
            if (characters.first() != ',') {
                throw IllegalStateException("Unexpected character: ${characters.first()}")
            }
            characters.removeFirst()
            val right = read(characters)
            if (characters.first() != ']') {
                throw IllegalStateException("Unexpected character: ${characters.first()}")
            }
            characters.removeFirst()
            return Pair(left, right)
        } else if (ch.isDigit()) {
            var value = ch.toString()
            while (characters.first().isDigit()) {
                value += characters.removeFirst()
            }
            return Regular(value.toLong())
        } else {
            throw IllegalStateException("Unexpected character: $ch")
        }
    }

    private fun read(input: List<String>): List<SnailfishNumber> {
        return input.map(this::read)
    }

    interface SnailfishNumber {

        fun getParent(): Pair?

        fun setParent(parent: Pair?)

        fun findNestedPair(depth: Int): Pair?

        fun findLargeRegular(): Regular?

        fun findLeftmostRegular(): Regular?

        fun findRightmostRegular(): Regular?

        fun magnitude(): Long
    }

    data class Pair(var left: SnailfishNumber, var right: SnailfishNumber) : SnailfishNumber {

        var parentPair: Pair? = null

        init {
            left.setParent(this)
            right.setParent(this)
        }

        override fun getParent(): Pair? {
            return parentPair
        }

        override fun setParent(parent: Pair?) {
            parentPair = parent
        }

        fun explode() {
            val nearestRegularOnLeft = findNearestRegularOnLeft()
            val nearestRegularOnRight = findNearestRegularOnRight()
            nearestRegularOnLeft?.add((left as Regular).value)
            nearestRegularOnRight?.add((right as Regular).value)
            parentPair!!.replace(this, Regular(0L))
        }

        fun findNearestRegularOnLeft(): Regular? {
            var node = this
            while (node.parentPair != null && (node.parentPair as Pair).left === node) {
                node = node.parentPair as Pair
            }
            return if (node.parentPair != null && (node.parentPair as Pair).left !== node) {
                (node.parentPair as Pair).left.findRightmostRegular()
            } else {
                null
            }
        }

        fun findNearestRegularOnRight(): Regular? {
            var node = this
            while (node.parentPair != null && (node.parentPair as Pair).right === node) {
                node = node.parentPair as Pair
            }
            return if (node.parentPair != null && (node.parentPair as Pair).right !== node) {
                (node.parentPair as Pair).right.findLeftmostRegular()
            } else {
                null
            }
        }

        override fun findNestedPair(depth: Int): Pair? {
            if (depth >= 5 && left is Regular && right is Regular) {
                return this
            } else {
                return left.findNestedPair(depth + 1) ?: right.findNestedPair(depth + 1)
            }
        }

        override fun findLargeRegular(): Regular? {
            return left.findLargeRegular() ?: right.findLargeRegular()
        }

        override fun findLeftmostRegular(): Regular? {
            return left.findLeftmostRegular() ?: right.findLeftmostRegular()
        }

        override fun findRightmostRegular(): Regular? {
            return right.findRightmostRegular() ?: left.findRightmostRegular()
        }

        fun replace(oldChild: SnailfishNumber, newChild: SnailfishNumber) {
            if (left === oldChild) {
                newChild.setParent(this)
                left = newChild
            } else if (right === oldChild) {
                newChild.setParent(this)
                right = newChild
            } else {
                throw IllegalStateException("Child node not found: $oldChild")
            }
        }

        override fun magnitude(): Long {
            return 3 * left.magnitude() + 2 * right.magnitude()
        }

        override fun toString(): String {
            return "[$left,$right]"
        }
    }

    data class Regular(var value: Long) : SnailfishNumber {

        var parentPair: Pair? = null

        override fun getParent(): Pair? {
            return parentPair
        }

        override fun setParent(parent: Pair?) {
            parentPair = parent
        }

        override fun findNestedPair(depth: Int): Pair? {
            return null
        }

        override fun findLargeRegular(): Regular? {
            return if (value >= 10) {
                this
            } else {
                null
            }
        }

        override fun findLeftmostRegular(): Regular {
            return this
        }

        override fun findRightmostRegular(): Regular {
            return this
        }

        fun add(otherValue: Long) {
            value += otherValue
        }

        fun split() {
            val pair = Pair(Regular(value / 2), Regular((value / 2) + (value % 2)))
            parentPair!!.replace(this, pair)
        }

        override fun magnitude(): Long {
            return value
        }

        override fun toString(): String {
            return value.toString()
        }
    }
}