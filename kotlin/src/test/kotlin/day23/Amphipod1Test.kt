package day23

import day23.Amphipod1.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Amphipod1Test {

    val logic = Amphipod1()

    /**
     * #############
     * #01234567890#
     * ###B#C#B#D###
     *   #A#D#C#A#
     *   #########
     *    1 3 5 7
     *    2 4 6 8
     */
    @Test
    fun go() {
        val amphipods = LinkedHashSet(listOf(
            Amphipod(0, AmphipodType.A, logic.cells[12]),
            Amphipod(1, AmphipodType.A, logic.cells[18]),
            Amphipod(2, AmphipodType.B, logic.cells[11]),
            Amphipod(3, AmphipodType.B, logic.cells[15]),
            Amphipod(4, AmphipodType.C, logic.cells[13]),
            Amphipod(5, AmphipodType.C, logic.cells[16]),
            Amphipod(6, AmphipodType.D, logic.cells[14]),
            Amphipod(7, AmphipodType.D, logic.cells[17])
        ))
        val situation = Situation(logic.cells, logic.moves, amphipods, 0)
        val resultCache = HashMap<Set<Amphipod>, Int>()
        val result = logic.move(situation, HashSet(), resultCache, 0)
        assertEquals(12521, result)
    }
}