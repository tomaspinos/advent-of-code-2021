package day19

import TestCommon
import day19.BeaconScanner1.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BeaconScanner1Test {

    val scanner = BeaconScanner1()

    @Test
    fun go() {
        val input = TestCommon.readInput("/day19/test-input.txt")
        val result = scanner.go(input)
        assertEquals(79, result)
    }

    @Test
    fun go2() {
        val input = TestCommon.readInput("/day19/test-input-2.txt")
        scanner.beaconMatchTreshold = 3
        val result = scanner.go(input)
        assertEquals(3, result)
    }

    @Test
    fun go3() {
        val input = TestCommon.readInput("/day19/test-input-3.txt")
        val result = scanner.go(input)
        assertEquals(6, result)
    }

    @Test
    fun rotation() {
        val rotation = Rotation(Axis.x, Axis.y, Axis.z)

        var point = Point(1, 2, 0)

        point = rotation.rotate(point)
        assertEquals(Point(-2, 1, 0), point)

        point = rotation.rotate(point)
        assertEquals(Point(-1, -2, 0), point)

        point = rotation.rotate(point)
        assertEquals(Point(2, -1, 0), point)
    }

    @Test
    fun scanner0() {
        val input = TestCommon.readInput("/day19/test-input-3.txt")
        scanner.beaconMatchTreshold = 6
        val result = scanner.go(input)
        assertEquals(6, result)
    }
}