package day20

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TrenchMap1Test {

    @Test
    fun go() {
        val input = TestCommon.readInput("/day20/test-input.txt")
        val result = TrenchMap1().go(input)
        assertEquals(35, result)
    }
}