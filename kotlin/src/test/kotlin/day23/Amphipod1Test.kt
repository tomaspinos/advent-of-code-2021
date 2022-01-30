package day23

import day23.Amphipod1.*
import day23.Amphipod1.Cell.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Amphipod1Test {

    val logic = Amphipod1()

    /**
     * #############
     * ###B#C#B#D###
     *   #A#D#C#A#
     *   #########
     *    1 3 5 7
     *    2 4 6 8
     */
    @Test
    fun go() {
        val amphipods = setOf(
            Amphipod(0, AmphipodType.A, A2),
            Amphipod(1, AmphipodType.A, D2),
            Amphipod(2, AmphipodType.B, A1),
            Amphipod(3, AmphipodType.B, C1),
            Amphipod(4, AmphipodType.C, B1),
            Amphipod(5, AmphipodType.C, C2),
            Amphipod(6, AmphipodType.D, B2),
            Amphipod(7, AmphipodType.D, D1)
        )
        val situation = Situation(amphipods)
        val result = logic.move(situation)
        assertEquals(12521, result)
    }
}