package day18

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Snailfish1Test {

    private val snailfish = Snailfish1()

    private val reader = SnailfishNumberReader()

    @Test
    fun magnitude() {
        val input = TestCommon.readInput("/day18/test-input.txt")
        val result = snailfish.magnitude(input)
        assertEquals(4140, result)
    }

    @Test
    fun add() {
        val a = reader.read("[[[[4,3],4],4],[7,[[8,4],9]]]")
        val b = reader.read("[1,1]")
        assertEquals(
            reader.read("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
            snailfish.add(a, b)
        )
    }

    @Test
    fun explode() {
        val number = reader.read("[[[[[9,8],1],2],3],4]") as SnailfishNumberPair
        val _9_8 = (((number.left as SnailfishNumberPair).left as SnailfishNumberPair).left as SnailfishNumberPair).left as SnailfishNumberPair
        _9_8.explode()
        assertEquals(reader.read("[[[[0,9],2],3],4]"), number)
    }

    @Test
    fun pairMagnitude() {
        val number = reader.read("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
        assertEquals(3488, number.magnitude())
    }

    @Test
    fun addMultiple1() {
        val result = snailfish.add(
            listOf(
                reader.read("[1,1]"),
                reader.read("[2,2]"),
                reader.read("[3,3]"),
                reader.read("[4,4]")
            )
        )
        assertEquals(reader.read("[[[[1,1],[2,2]],[3,3]],[4,4]]"), result)
    }

    @Test
    fun addMultiple2() {
        val result = snailfish.add(
            listOf(
                reader.read("[1,1]"),
                reader.read("[2,2]"),
                reader.read("[3,3]"),
                reader.read("[4,4]"),
                reader.read("[5,5]")
            )
        )
        assertEquals(reader.read("[[[[3,0],[5,3]],[4,4]],[5,5]]"), result)
    }

    @Test
    fun addMultiple3() {
        val result = snailfish.add(
            listOf(
                reader.read("[1,1]"),
                reader.read("[2,2]"),
                reader.read("[3,3]"),
                reader.read("[4,4]"),
                reader.read("[5,5]"),
                reader.read("[6,6]")
            )
        )
        assertEquals(reader.read("[[[[5,0],[7,4]],[5,5]],[6,6]]"), result)
    }

    @Test
    fun addMultiple4() {
        val result = snailfish.add(
            listOf(
                reader.read("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"),
                reader.read("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"),
                reader.read("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"),
                reader.read("[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]"),
                reader.read("[7,[5,[[3,8],[1,4]]]]"),
                reader.read("[[2,[2,2]],[8,[8,1]]]"),
                reader.read("[2,9]"),
                reader.read("[1,[[[9,3],9],[[9,0],[0,7]]]]"),
                reader.read("[[[5,[7,4]],7],1]"),
                reader.read("[[[[4,2],2],6],[8,7]]")
            )
        )
        assertEquals(reader.read("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"), result)
    }
}