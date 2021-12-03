package day03

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BinDiag2Test {

    @Test
    fun diag() {
        val input = TestCommon.readInput("/day03/test-input.txt")
        val result = BinDiag2().diag(input)
        assertEquals(BinDiag2.Result(23, 10), result)
    }
}