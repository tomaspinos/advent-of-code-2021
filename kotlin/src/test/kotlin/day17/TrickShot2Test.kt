package day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TrickShot2Test {

    @Test
    fun go() {
        val result = TrickShot2().go(TrickShot2.Area(TrickShot2.Point(20, -5), TrickShot2.Point(30, -10)))
        assertEquals(112, result)
    }
}