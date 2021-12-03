package day03

import TestCommon
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BinDiag1Test {

    @Test
    fun diag() {
        val input = TestCommon.readInput("/day03/test-input.txt")
        val result = BinDiag1().diag(input)
        assertEquals(BinDiag1.Result(22, 9), result)
    }
}