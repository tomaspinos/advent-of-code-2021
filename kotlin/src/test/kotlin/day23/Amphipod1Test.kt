package day23

import day23.Amphipod1.*
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
            Amphipod(0, AmphipodType.A, 12),
            Amphipod(1, AmphipodType.A, 18),
            Amphipod(2, AmphipodType.B, 11),
            Amphipod(3, AmphipodType.B, 15),
            Amphipod(4, AmphipodType.C, 13),
            Amphipod(5, AmphipodType.C, 16),
            Amphipod(6, AmphipodType.D, 14),
            Amphipod(7, AmphipodType.D, 17)
        )
        val situation = Situation(amphipods)
        val result = logic.move(situation)
        assertEquals(12521, result)
    }
}