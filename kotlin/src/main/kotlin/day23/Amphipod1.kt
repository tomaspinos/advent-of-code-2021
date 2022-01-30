package day23

import day23.Amphipod1.Amphipod
import day23.Amphipod1.AmphipodType.*
import day23.Amphipod1.Cell.*
import day23.Amphipod1.Situation
import java.util.*

/**
 * #############
 * #...........#
 * ###A#C#B#D###
 *   #B#A#D#C#
 *   #########
 *    1 3 5 7
 *    2 4 6 8
 */
fun main() {
    val logic = Amphipod1()
    val amphipods = setOf(
        Amphipod(0, A, A1),
        Amphipod(1, A, B2),
        Amphipod(2, B, A2),
        Amphipod(3, B, C1),
        Amphipod(4, C, B1),
        Amphipod(5, C, D2),
        Amphipod(6, D, C2),
        Amphipod(7, D, D1)
    )
    val situation = Situation(amphipods)
    val result = logic.move(situation)
    println(result)
}

class Amphipod1 {

    enum class AmphipodType(val moveEnergy: Int) {

        A(1),
        B(10),
        C(100),
        D(1000)
    }

    enum class Cell(val homeOf: AmphipodType? = null) {

        H0, H1, H2, H3, H4, H5, H6, H7, H8, H9, H10,
        A1(A), A2(A),
        B1(B), B2(B),
        C1(C), C2(C),
        D1(D), D2(D)
    }

    companion object {

        val allAmphipodTypes = AmphipodType.values().toSet()

        val homesA = listOf(A1, A2)
        val homesB = listOf(B1, B2)
        val homesC = listOf(C1, C2)
        val homesD = listOf(D1, D2)

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
            return result
        }

        private fun flatten(vararg lists: List<PossibleMove>): List<PossibleMove> {
            return lists.flatMap { list -> list }
        }

        class PossibleMoveBuilder(private val amphipodTypes: Set<AmphipodType>) {

            private val steps: MutableList<List<Cell>> = ArrayList()

            fun steps(vararg cells: Cell): PossibleMoveBuilder {
                steps.add(cells.toList())
                return this
            }

            fun build(): List<PossibleMove> {
                val result = ArrayList<PossibleMove>()
                val partialSteps = ArrayList<Cell>()
                for (stepSegment in steps) {
                    partialSteps.addAll(stepSegment)
                    val steps = partialSteps.toList()
                    result.add(PossibleMove(steps, amphipodTypes))
                }
                return result
            }
        }

        private fun possibleMoveBuilder(amphipodTypes: Set<AmphipodType>): PossibleMoveBuilder {
            return PossibleMoveBuilder(amphipodTypes)
        }

        /**
         * #############
         * #01234567890#
         * ###1#3#5#7###
         *   #2#4#6#8#
         *   #########
         */
        val moves = mapOf(
            Pair(H0, emptyList()),
            Pair(
                H1,
                flatten(
                    possibleMoves(A, listOf(H2), homesA),
                    possibleMoves(B, listOf(H2, H3, H4), homesB),
                    possibleMoves(C, listOf(H2, H3, H4, H5, H6), homesC),
                    possibleMoves(D, listOf(H2, H3, H4, H5, H6, H7, H8), homesD)
                )
            ),
            Pair(
                H3,
                flatten(
                    possibleMoves(A, listOf(H2), homesA),
                    possibleMoves(B, listOf(H4), homesB),
                    possibleMoves(C, listOf(H4, H5, H6), homesC),
                    possibleMoves(D, listOf(H4, H5, H6, H7, H8), homesD)
                )
            ),
            Pair(
                H5,
                flatten(
                    possibleMoves(B, listOf(H4), homesB),
                    possibleMoves(A, listOf(H4, H3, H2), homesA),
                    possibleMoves(C, listOf(H6), homesC),
                    possibleMoves(D, listOf(H6, H7, H8), homesD)
                )
            ),
            Pair(
                H7,
                flatten(
                    possibleMoves(C, listOf(H6), homesC),
                    possibleMoves(B, listOf(H6, H5, H4), homesB),
                    possibleMoves(A, listOf(H6, H5, H4, H3, H2), homesA),
                    possibleMoves(D, listOf(H8), homesD)
                )
            ),
            Pair(
                H9,
                flatten(
                    possibleMoves(D, listOf(H8), homesD),
                    possibleMoves(C, listOf(H8, H7, H6), homesC),
                    possibleMoves(B, listOf(H8, H7, H6, H5, H4), homesB),
                    possibleMoves(A, listOf(H8, H7, H6, H5, H4, H3, H2), homesA)
                )
            ),
            Pair(H10, emptyList()),
            Pair(
                A1,
                flatten(
                    possibleMoveBuilder(setOf(A)).steps(A2).build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H2, H1).build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H2, H3).steps(H4, H5).steps(H6, H7).steps(H8, H9)
                        .build()
                )
            ),
            Pair(
                A2,
                flatten(
                    possibleMoveBuilder(setOf(B, C, D)).steps(A1, H2, H1).build(),
                    possibleMoveBuilder(setOf(B, C, D)).steps(A1, H2, H3).steps(H4, H5).steps(H6, H7).steps(H8, H9)
                        .build()
                )
            ),
            Pair(
                B1,
                flatten(
                    possibleMoveBuilder(setOf(B)).steps(B2).build(),
                    possibleMoveBuilder(setOf(B, C, D)).steps(H4, H3).steps(H2, H1).build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H4, H5).steps(H6, H7).steps(H8, H9).build()
                )
            ),
            Pair(
                B2,
                flatten(
                    possibleMoveBuilder(setOf(A, C, D)).steps(B1, H4, H3).steps(H2, H1).build(),
                    possibleMoveBuilder(setOf(A, C, D)).steps(B1, H4, H5).steps(H6, H7).steps(H8, H9).build()
                )
            ),
            Pair(
                C1,
                flatten(
                    possibleMoveBuilder(setOf(C)).steps(C2).build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H6, H5).steps(H4, H3).steps(H2, H1).build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H6, H7).steps(H8, H9).build()
                )
            ),
            Pair(
                C2,
                flatten(
                    possibleMoveBuilder(setOf(A, B, D)).steps(C1, H6, H5).steps(H4, H3).steps(H2, H1).build(),
                    possibleMoveBuilder(setOf(A, B, D)).steps(C1, H6, H7).steps(H8, H9).build()
                )
            ),
            Pair(
                D1,
                flatten(
                    possibleMoveBuilder(setOf(D)).steps(D2).build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H8, H7).steps(H6, H5).steps(H4, H3).steps(H2, H1)
                        .build(),
                    possibleMoveBuilder(allAmphipodTypes).steps(H8, H9).build()
                )
            ),
            Pair(
                D2,
                flatten(
                    possibleMoveBuilder(setOf(A, B, C)).steps(D1, H8, H7).steps(H6, H5).steps(H4, H3).steps(H2, H1)
                        .build(),
                    possibleMoveBuilder(setOf(A, B, C)).steps(D1, H8, H9).build()
                )
            )
        )
    }

    fun move(initialSituation: Situation): Int {
        val costMap = HashMap<Situation, Int>()

        val queue =
            PriorityQueue<Situation> { s1, s2 -> (costMap[s1] ?: Int.MAX_VALUE) - (costMap[s2] ?: Int.MAX_VALUE) }

        costMap[initialSituation] = 0
        queue.add(initialSituation)

        while (queue.isNotEmpty()) {
            val currentSituation = queue.remove()

            val possibleMoves = currentSituation.getPossibleMoves()
            for (move in possibleMoves) {
                val newSituation = currentSituation.move(move.amphipod, move.cell)
                val cost = costMap[currentSituation]!! + move.energy
                if (cost < (costMap[newSituation] ?: Int.MAX_VALUE)) {
                    costMap[newSituation] = cost
                    queue.add(newSituation)
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
                A -> amphipodsByCell[A1]?.type == A && amphipodsByCell[A2]?.type == A
                B -> amphipodsByCell[B1]?.type == B && amphipodsByCell[B2]?.type == B
                C -> amphipodsByCell[C1]?.type == C && amphipodsByCell[C2]?.type == C
                D -> amphipodsByCell[D1]?.type == D && amphipodsByCell[D2]?.type == D
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