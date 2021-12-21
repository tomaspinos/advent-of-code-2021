package day21

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DiracDice2Test {

    val diracDice = DiracDice2()

    @Test
    fun go() {
        val result = diracDice.go(4, 8)
        assertEquals(444356092776315, result)
    }
}