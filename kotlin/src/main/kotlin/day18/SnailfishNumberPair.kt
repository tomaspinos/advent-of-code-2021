package day18

data class SnailfishNumberPair(var left: SnailfishNumber, var right: SnailfishNumber) : SnailfishNumber {

    override var parent: SnailfishNumberPair? = null

    init {
        left.parent = this
        right.parent = this
    }

    fun reduce() {
        var continueReducing = true
        while (continueReducing) {
            continueReducing = false
            val pairToExplode = findNestedPair(1)
            if (pairToExplode != null) {
                pairToExplode.explode()
                continueReducing = true
            } else {
                val largeRegular = findLargeRegular()
                if (largeRegular != null) {
                    largeRegular.split()
                    continueReducing = true
                }
            }
        }
    }

    fun explode() {
        findNearestRegularOnLeft()?.add((left as SnailfishNumberRegular).value)
        findNearestRegularOnRight()?.add((right as SnailfishNumberRegular).value)
        parent!!.replace(this, SnailfishNumberRegular(0L))
    }

    override fun findNestedPair(depth: Int): SnailfishNumberPair? {
        return if (depth >= 5 && left is SnailfishNumberRegular && right is SnailfishNumberRegular) {
            this
        } else {
            left.findNestedPair(depth + 1) ?: right.findNestedPair(depth + 1)
        }
    }

    override fun findLargeRegular(): SnailfishNumberRegular? {
        return left.findLargeRegular() ?: right.findLargeRegular()
    }

    override fun findLeftmostRegular(): SnailfishNumberRegular? {
        return left.findLeftmostRegular() ?: right.findLeftmostRegular()
    }

    override fun findRightmostRegular(): SnailfishNumberRegular? {
        return right.findRightmostRegular() ?: left.findRightmostRegular()
    }

    fun replace(oldChild: SnailfishNumber, newChild: SnailfishNumber) {
        if (left === oldChild) {
            newChild.parent = this
            left = newChild
        } else if (right === oldChild) {
            newChild.parent = this
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

    private fun findNearestRegularOnLeft(): SnailfishNumberRegular? {
        var node = this
        while (node.parent != null && node.parent!!.left === node) {
            node = node.parent!!
        }
        return if (node.parent != null && node.parent!!.left !== node) {
            node.parent!!.left.findRightmostRegular()
        } else {
            null
        }
    }

    private fun findNearestRegularOnRight(): SnailfishNumberRegular? {
        var node = this
        while (node.parent != null && node.parent!!.right === node) {
            node = node.parent!!
        }
        return if (node.parent != null && node.parent!!.right !== node) {
            node.parent!!.right.findLeftmostRegular()
        } else {
            null
        }
    }
}