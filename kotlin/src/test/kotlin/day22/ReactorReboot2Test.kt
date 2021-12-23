package day22

import TestCommon
import day22.ReactorReboot2.Cuboid
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ReactorReboot2Test {

    val reactorReboot = ReactorReboot2()

    @Test
    fun go() {
        val input = TestCommon.readInput("/day22/test-input2.txt")
        val result = reactorReboot.go(input)
        assertEquals(2758514936282235, result)
    }

    @Test
    fun readStep() {
        assertEquals(
            Cuboid(true, LongRange(-54112, -39298), LongRange(-85059, -49293), LongRange(-27449, 7877)),
            reactorReboot.readStep("on x=-54112..-39298,y=-85059..-49293,z=-27449..7877")
        )
        assertEquals(
            Cuboid(false, LongRange(18, 30), LongRange(-20, -8), LongRange(-3, 13)),
            reactorReboot.readStep("off x=18..30,y=-20..-8,z=-3..13")
        )
    }

//    @Test
//    fun stepIntersectionDimension() {
//        val cuboid1 = Cuboid(true, (0L..9), (0L..9), (0L..9))
//        assertEquals(
//            1000L,
//            cuboid1.intersectionDimension(cuboid1)
//        )
//        assertEquals(
//            1L,
//            cuboid1.intersectionDimension(Cuboid(true, (0L..0), (0L..0), (0L..0)))
//        )
//        assertEquals(
//            1L,
//            cuboid1.intersectionDimension(Cuboid(true, (9L..19L), (9L..19L), (9L..19L)))
//        )
//        assertEquals(
//            0L,
//            cuboid1.intersectionDimension(Cuboid(true, (10L..10L), (10L..10L), (10L..10L)))
//        )
//    }
}