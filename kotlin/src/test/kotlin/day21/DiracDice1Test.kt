package day21

import day21.DiracDice1.Dice
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DiracDice1Test {

    val diracDice = DiracDice1()

    @Test
    fun go() {
        val result = diracDice.go(4, 8)
        assertEquals(739785, result)
    }

    @Test
    fun nextPosition() {
        assertEquals(6, diracDice.nextPosition(0, Dice()))
        assertEquals(9, diracDice.nextPosition(3, Dice()))
        assertEquals(0, diracDice.nextPosition(4, Dice()))
    }

    @Test
    fun diceNext0() {
        val dice = Dice(0, 0)
        assertEquals(1, dice.next())
        assertEquals(2, dice.next())
        assertEquals(3, dice.next())
        assertEquals(3, dice.rolls)
    }

    @Test
    fun diceNext99() {
        val dice = Dice(99, 0)
        assertEquals(100, dice.next())
        assertEquals(1, dice.next())
        assertEquals(2, dice.next())
        assertEquals(3, dice.rolls)
    }
}