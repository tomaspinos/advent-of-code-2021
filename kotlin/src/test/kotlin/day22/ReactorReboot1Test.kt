package day22

import TestCommon
import day22.ReactorReboot1.Step
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ReactorReboot1Test {

    val reactorReboot = ReactorReboot1()

    @Test
    fun go() {
        val input = TestCommon.readInput("/day22/test-input1.txt")
        val result = reactorReboot.go(input)
        assertEquals(590784, result)
    }

    @Test
    fun readStep() {
        assertEquals(
            Step(true, IntRange(-54112, -39298), IntRange(-85059, -49293), IntRange(-27449, 7877)),
            reactorReboot.readStep("on x=-54112..-39298,y=-85059..-49293,z=-27449..7877"))
        assertEquals(
            Step(false, IntRange(18, 30), IntRange(-20, -8), IntRange(-3, 13)),
            reactorReboot.readStep("off x=18..30,y=-20..-8,z=-3..13"))
    }

    @Test
    fun stepContains() {
        val step = Step(true, (0..9), (0..9), (0..9))
        assertTrue(step.contains(0, 0, 0))
        assertTrue(step.contains(9, 9, 9))
        assertFalse(step.contains(0, 0, 10))
    }
}