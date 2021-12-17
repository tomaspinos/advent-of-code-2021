package day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TrickShot1Test {

    @Test
    fun go() {
        val result = TrickShot1().go(TrickShot1.Area(TrickShot1.Point(20, -5), TrickShot1.Point(30, -10)))
        assertEquals(45, result)
    }
}