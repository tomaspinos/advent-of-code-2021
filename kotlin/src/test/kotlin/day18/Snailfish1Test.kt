package day18

import TestCommon
import day18.Snailfish1.Pair
import day18.Snailfish1.Regular
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Snailfish1Test {

    val snailfish = Snailfish1()

    @Test
    fun magnitude() {
        val input = TestCommon.readInput("/day18/test-input.txt")
        val result = snailfish.magnitude(input)
        assertEquals(4140, result)
    }

    @Test
    fun read() {
        assertEquals(
            Pair(Regular(1), Regular(2)),
            snailfish.read("[1,2]")
        )
        assertEquals(
            Pair(Pair(Regular(1), Regular(2)), Regular(3)),
            snailfish.read("[[1,2],3]")
        )
        assertEquals(
            Pair(
                Pair(
                    Pair(
                        Pair(
                            Regular(1),
                            Regular(2)
                        ),
                        Pair(
                            Regular(3),
                            Regular(4)
                        )
                    ),
                    Pair(
                        Pair(
                            Regular(5),
                            Regular(6)
                        ),
                        Pair(
                            Regular(7),
                            Regular(8)
                        )
                    )
                ),
                Regular(9)
            ),
            snailfish.read("[[[[1,2],[3,4]],[[5,6],[7,8]]],9]")
        )
    }

    @Test
    fun add() {
        val a = snailfish.read("[[[[4,3],4],4],[7,[[8,4],9]]]")
        val b = snailfish.read("[1,1]")
        assertEquals(
            snailfish.read("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
            snailfish.add(a, b)
        )
    }

    @Test
    fun explode() {
        val number = snailfish.read("[[[[[9,8],1],2],3],4]") as Pair
        val _9_8 = (((number.left as Pair).left as Pair).left as Pair).left as Pair
        _9_8.explode()
        assertEquals(snailfish.read("[[[[0,9],2],3],4]"), number)
    }

    @Test
    fun pairMagnitude() {
        val number = snailfish.read("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
        assertEquals(3488, number.magnitude())
    }

    @Test
    fun addMultiple1() {
        val result = snailfish.add(
            listOf(
                snailfish.read("[1,1]"),
                snailfish.read("[2,2]"),
                snailfish.read("[3,3]"),
                snailfish.read("[4,4]")
            )
        )
        assertEquals(snailfish.read("[[[[1,1],[2,2]],[3,3]],[4,4]]"), result)
    }

    @Test
    fun addMultiple2() {
        val result = snailfish.add(
            listOf(
                snailfish.read("[1,1]"),
                snailfish.read("[2,2]"),
                snailfish.read("[3,3]"),
                snailfish.read("[4,4]"),
                snailfish.read("[5,5]")
            )
        )
        assertEquals(snailfish.read("[[[[3,0],[5,3]],[4,4]],[5,5]]"), result)
    }

    @Test
    fun addMultiple3() {
        val result = snailfish.add(
            listOf(
                snailfish.read("[1,1]"),
                snailfish.read("[2,2]"),
                snailfish.read("[3,3]"),
                snailfish.read("[4,4]"),
                snailfish.read("[5,5]"),
                snailfish.read("[6,6]")
            )
        )
        assertEquals(snailfish.read("[[[[5,0],[7,4]],[5,5]],[6,6]]"), result)
    }

    @Test
    fun addMultiple4() {
        val result = snailfish.add(
            listOf(
                snailfish.read("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"),
                snailfish.read("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"),
                snailfish.read("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"),
                snailfish.read("[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]"),
                snailfish.read("[7,[5,[[3,8],[1,4]]]]"),
                snailfish.read("[[2,[2,2]],[8,[8,1]]]"),
                snailfish.read("[2,9]"),
                snailfish.read("[1,[[[9,3],9],[[9,0],[0,7]]]]"),
                snailfish.read("[[[5,[7,4]],7],1]"),
                snailfish.read("[[[[4,2],2],6],[8,7]]")
            )
        )
        assertEquals(snailfish.read("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"), result)
    }
}