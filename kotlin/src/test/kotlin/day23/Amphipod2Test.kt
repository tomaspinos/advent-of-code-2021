package day23

import day23.Amphipod2.*
import day23.Amphipod2.AmphipodType.*
import day23.Amphipod2.Cell.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Amphipod2Test {

    val logic = Amphipod2()

    /**
     * #############
     * ###B#C#B#D###
     *   #D#C#B#A#
     *   #D#B#A#C#
     *   #A#D#C#A#
     *   #########
     *    1 3 5 7
     *    2 4 6 8
     */
    @Test
    fun go() {
        val amphipods = setOf(
            Amphipod(0, A, A4),
            Amphipod(1, A, C3),
            Amphipod(2, A, D2),
            Amphipod(3, A, D4),

            Amphipod(4, B, A1),
            Amphipod(5, B, B3),
            Amphipod(6, B, C1),
            Amphipod(7, B, C2),

            Amphipod(8, C, B1),
            Amphipod(9, C, B2),
            Amphipod(10, C, C4),
            Amphipod(11, C, D3),

            Amphipod(12, D, A2),
            Amphipod(13, D, A3),
            Amphipod(14, D, B4),
            Amphipod(15, D, D1)
        )
        val situation = Situation(amphipods)
        val result = logic.move(situation)
        assertEquals(44169, result)
    }
}