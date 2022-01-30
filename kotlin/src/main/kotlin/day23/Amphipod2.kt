package day23

import day23.Amphipod2.Amphipod
import day23.Amphipod2.AmphipodType.*
import day23.Amphipod2.Cell.*
import day23.Amphipod2.Situation
import java.util.*

/**
 * #############
 * #...........#
 * ###A#C#B#D###
 *   #D#C#B#A#
 *   #D#B#A#C#
 *   #B#A#D#C#
 *   #########
 *    1 3 5 7
 *    2 4 6 8
 */
fun main() {
    val logic = Amphipod2()
    val amphipods = setOf(
        Amphipod(0, A, A1),
        Amphipod(1, A, B4),
        Amphipod(2, A, C3),
        Amphipod(3, A, D2),

        Amphipod(4, B, A4),
        Amphipod(5, B, B3),
        Amphipod(6, B, C1),
        Amphipod(7, B, C2),

        Amphipod(8, C, B1),
        Amphipod(9, C, B2),
        Amphipod(10, C, D3),
        Amphipod(11, C, D4),

        Amphipod(12, D, A2),
        Amphipod(13, D, A3),
        Amphipod(14, D, C4),
        Amphipod(15, D, D1)
    )
    val situation = Situation(amphipods)
    val result = logic.move(situation)
    println(result)
}

class Amphipod2 {

    enum class AmphipodType(val moveEnergy: Int) {

        A(1),
        B(10),
        C(100),
        D(1000)
    }

    enum class Cell {

        H0, H1, H2, H3, H4, H5, H6, H7, H8, H9, H10,
        A1, A2, A3, A4,
        B1, B2, B3, B4,
        C1, C2, C3, C4,
        D1, D2, D3, D4
    }

    companion object {

        val allAmphipodTypes = AmphipodType.values().toSet()

        val homesA = listOf(A1, A2, A3, A4)
        val homesB = listOf(B1, B2, B3, B4)
        val homesC = listOf(C1, C2, C3, C4)
        val homesD = listOf(D1, D2, D3, D4)

        private fun possibleMoves(amphipodType: AmphipodType, hall: List<Cell>, home: List<Cell>): List<PossibleMove> {
            val result = ArrayList<PossibleMove>()
            val homeSteps = ArrayList<Cell>()
            for (homeCell in home) {
                homeSteps.add(homeCell)
                val steps = ArrayList<Cell>()
                steps.addAll(hall)
                steps.addAll(homeSteps)
                result.add(PossibleMove(steps, setOf(amphipodType)))
            }
            return result.reversed()
        }

        private fun flatten(vararg lists: List<PossibleMove>): List<PossibleMove> {
            return lists.flatMap { list -> list }
        }

        private fun possibleMovesFromHome(
            other: Set<AmphipodType>,
            homeCells: List<Cell>,
            hallToLeft: List<Cell>,
            hallToRight: List<Cell>
        ): Map<Cell, List<PossibleMove>> {
            val pairs = ArrayList<Pair<Cell, PossibleMove>>()

            var partialHallToLeft = emptyList<Cell>()
            for (window in hallToLeft.windowed(2, 2, true)) {
                partialHallToLeft = partialHallToLeft + window
                pairs.add(Pair(homeCells[0], PossibleMove(partialHallToLeft, allAmphipodTypes)))
                pairs.add(Pair(homeCells[1], PossibleMove(listOf(homeCells[0]) + partialHallToLeft, allAmphipodTypes)))
                pairs.add(Pair(homeCells[2], PossibleMove(listOf(homeCells[1], homeCells[0]) + partialHallToLeft, allAmphipodTypes)))
                pairs.add(Pair(homeCells[3], PossibleMove(listOf(homeCells[2], homeCells[1], homeCells[0]) + partialHallToLeft, other)))
            }

            var partialHallToRight = emptyList<Cell>()
            for (window in hallToRight.windowed(2, 2, true)) {
                partialHallToRight = partialHallToRight + window
                pairs.add(Pair(homeCells[0], PossibleMove(partialHallToRight, allAmphipodTypes)))
                pairs.add(Pair(homeCells[1], PossibleMove(listOf(homeCells[0]) + partialHallToRight, allAmphipodTypes)))
                pairs.add(Pair(homeCells[2], PossibleMove(listOf(homeCells[1], homeCells[0]) + partialHallToRight, allAmphipodTypes)))
                pairs.add(Pair(homeCells[3], PossibleMove(listOf(homeCells[2], homeCells[1], homeCells[0]) + partialHallToRight, other)))
            }

            return pairs.groupBy { (cell, _) -> cell }.mapValues { (_, listOfPairs) -> listOfPairs.map { (_, list) -> list } }
        }

        /**
         * #############
         * #01234567890#
         * ###1#3#5#7###
         *   #2#4#6#8#
         *   #########
         */
        val moves = createPossibleMoves()

        private fun createPossibleMoves(): Map<Cell, List<PossibleMove>> {
            val map = HashMap<Cell, List<PossibleMove>>()
            map[H0] = flatten(
                possibleMoves(A, listOf(H1, H2), homesA),
                possibleMoves(B, listOf(H1, H2, H3, H4), homesB),
                possibleMoves(C, listOf(H1, H2, H3, H4, H5, H6), homesC),
                possibleMoves(D, listOf(H1, H2, H3, H4, H5, H6, H7, H8), homesD)
            )
            map[H1] = flatten(
                possibleMoves(A, listOf(H2), homesA),
                possibleMoves(B, listOf(H2, H3, H4), homesB),
                possibleMoves(C, listOf(H2, H3, H4, H5, H6), homesC),
                possibleMoves(D, listOf(H2, H3, H4, H5, H6, H7, H8), homesD)
            )
            map[H3] = flatten(
                possibleMoves(A, listOf(H2), homesA),
                possibleMoves(B, listOf(H4), homesB),
                possibleMoves(C, listOf(H4, H5, H6), homesC),
                possibleMoves(D, listOf(H4, H5, H6, H7, H8), homesD)
            )
            map[H5] = flatten(
                possibleMoves(B, listOf(H4), homesB),
                possibleMoves(A, listOf(H4, H3, H2), homesA),
                possibleMoves(C, listOf(H6), homesC),
                possibleMoves(D, listOf(H6, H7, H8), homesD)
            )
            map[H7] = flatten(
                possibleMoves(C, listOf(H6), homesC),
                possibleMoves(B, listOf(H6, H5, H4), homesB),
                possibleMoves(A, listOf(H6, H5, H4, H3, H2), homesA),
                possibleMoves(D, listOf(H8), homesD)
            )
            map[H9] = flatten(
                possibleMoves(D, listOf(H8), homesD),
                possibleMoves(C, listOf(H8, H7, H6), homesC),
                possibleMoves(B, listOf(H8, H7, H6, H5, H4), homesB),
                possibleMoves(A, listOf(H8, H7, H6, H5, H4, H3, H2), homesA)
            )
            map[H10] = flatten(
                possibleMoves(D, listOf(H9, H8), homesD),
                possibleMoves(C, listOf(H9, H8, H7, H6), homesC),
                possibleMoves(B, listOf(H9, H8, H7, H6, H5, H4), homesB),
                possibleMoves(A, listOf(H9, H8, H7, H6, H5, H4, H3, H2), homesA)
            )
            map.putAll(possibleMovesFromHome(setOf(B, C, D), homesA, listOf(H2, H1, H0), listOf(H2, H3, H4, H5, H6, H7, H8, H9, H10)))
            map.putAll(possibleMovesFromHome(setOf(A, C, D), homesB, listOf(H4, H3, H2, H1, H0), listOf(H4, H5, H6, H7, H8, H9, H10)))
            map.putAll(possibleMovesFromHome(setOf(A, B, D), homesC, listOf(H6, H5, H4, H3, H2, H1, H0), listOf(H6, H7, H8, H9, H10)))
            map.putAll(possibleMovesFromHome(setOf(A, B, C), homesD, listOf(H8, H7, H6, H5, H4, H3, H2, H1, H0), listOf(H8, H9, H10)))
            return map
        }
    }

    fun move(initialSituation: Situation): Int {
        val costMap = HashMap<Situation, Int>()

        val queue =
            PriorityQueue<Situation> { s1, s2 -> (costMap[s1] ?: Int.MAX_VALUE) - (costMap[s2] ?: Int.MAX_VALUE) }

        val allSituations = HashSet<Situation>()

        costMap[initialSituation] = 0
        queue.add(initialSituation)
        allSituations.add(initialSituation)

        while (queue.isNotEmpty()) {
            val currentSituation = queue.remove()

            val possibleMoves = currentSituation.getPossibleMoves()
            for (move in possibleMoves) {
                val newSituation = currentSituation.move(move.amphipod, move.cell)
                if (!allSituations.contains(newSituation)) {
                    allSituations.add(newSituation)
                    val cost = costMap[currentSituation]!! + move.energy
                    if (cost < (costMap[newSituation] ?: Int.MAX_VALUE)) {
                        costMap[newSituation] = cost
                        queue.add(newSituation)
                    }
                    if (newSituation.isDone()) {
                        println(costMap[newSituation])
                    }
                }
            }
        }

        return costMap.filter { (situation, _) -> situation.isDone() }.map { (_, cost) -> cost }.first()
    }

    data class Situation(val amphipods: Set<Amphipod>) {

        val key = SituationKey(
            amphipods
                .map { amphipod -> Pair(amphipod.type, amphipod.cell) }
                .toSet())

        val occupiedCells = amphipods.map { amphipod -> amphipod.cell }.toSet()

        val amphipodsByCell =
            amphipods.groupBy { amphipod -> amphipod.cell }.mapValues { (_, list) -> list.first() }

        fun isDone(): Boolean {
            return amphipods.all(this@Situation::isOnTarget)
        }

        fun getPossibleMoves(): List<Move> {
            return amphipods
                .flatMap { amphipod ->
                    if (isOnTarget(amphipod)) emptyList()
                    else moves[amphipod.cell]!!
                        .filter { move ->
                            move.permittedAmphipodTypes.contains(amphipod.type)
                                    && move.steps.all { step -> !occupiedCells.contains(step) }
                        }
                        .map { move ->
                            Move(
                                amphipod,
                                move.steps.last(),
                                amphipod.type.moveEnergy * move.steps.size
                            )
                        }
                }
        }

        fun move(amphipod: Amphipod, cell: Cell): Situation {
            val movedAmphipod = amphipod.at(cell)
            val newAmphipods = LinkedHashSet(amphipods)
            newAmphipods.remove(amphipod)
            newAmphipods.add(movedAmphipod)
            return Situation(newAmphipods)
        }

        override fun equals(other: Any?): Boolean {
            return other is Situation && key == other.key
        }

        override fun hashCode(): Int {
            return key.hashCode()
        }

        private fun isOnTarget(amphipod: Amphipod): Boolean {
            return when (amphipod.type) {
                A -> amphipodsByCell[A1]?.type == A && amphipodsByCell[A2]?.type == A && amphipodsByCell[A3]?.type == A && amphipodsByCell[A4]?.type == A
                B -> amphipodsByCell[B1]?.type == B && amphipodsByCell[B2]?.type == B && amphipodsByCell[B3]?.type == B && amphipodsByCell[B4]?.type == B
                C -> amphipodsByCell[C1]?.type == C && amphipodsByCell[C2]?.type == C && amphipodsByCell[C3]?.type == C && amphipodsByCell[C4]?.type == C
                D -> amphipodsByCell[D1]?.type == D && amphipodsByCell[D2]?.type == D && amphipodsByCell[D3]?.type == D && amphipodsByCell[D4]?.type == D
            }
        }
    }

    data class Amphipod(val index: Int, val type: AmphipodType, val cell: Cell) {

        fun at(newCell: Cell): Amphipod {
            return Amphipod(index, type, newCell)
        }

        override fun toString(): String {
            return "$index,$type,${cell}"
        }
    }

    data class Move(val amphipod: Amphipod, val cell: Cell, val energy: Int)

    data class PossibleMove(val steps: List<Cell>, val permittedAmphipodTypes: Set<AmphipodType>)

    data class SituationKey(val positions: Set<Pair<AmphipodType, Cell>>)
}