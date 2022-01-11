package day23

import day23.Amphipod1.Amphipod
import day23.Amphipod1.AmphipodType.*
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
        Amphipod(0, A, 11),
        Amphipod(1, A, 14),
        Amphipod(2, B, 12),
        Amphipod(3, B, 15),
        Amphipod(4, C, 13),
        Amphipod(5, C, 18),
        Amphipod(6, D, 16),
        Amphipod(7, D, 17)
    )
    val situation = Situation(amphipods)
    val result = logic.move(situation)
    println(result)
}

class Amphipod1 {

    companion object {

        val allAmphipodTypes = values().toSet()

        /**
         * #############
         * #01234567890#
         * ###1#3#5#7###
         *   #2#4#6#8#
         *   #########
         */
        val moves = mapOf(
            Pair(0, emptyList()),
            Pair(
                1,
                listOf(
                    PossibleMove(listOf(2, 11), setOf(A)),
                    PossibleMove(listOf(2, 11, 12), setOf(A)),
                    PossibleMove(listOf(2, 3, 4, 13), setOf(B)),
                    PossibleMove(listOf(2, 3, 4, 13, 14), setOf(B)),
                    PossibleMove(listOf(2, 3, 4, 5, 6, 15), setOf(C)),
                    PossibleMove(listOf(2, 3, 4, 5, 6, 15, 16), setOf(C)),
                    PossibleMove(listOf(2, 3, 4, 5, 6, 7, 8, 17), setOf(D)),
                    PossibleMove(listOf(2, 3, 4, 5, 6, 7, 8, 17, 18), setOf(D))
                )
            ),
            Pair(
                3,
                listOf(
                    PossibleMove(listOf(2, 11), setOf(A)),
                    PossibleMove(listOf(2, 11, 12), setOf(A)),
                    PossibleMove(listOf(4, 13), setOf(B)),
                    PossibleMove(listOf(4, 13, 14), setOf(B)),
                    PossibleMove(listOf(4, 5, 6, 15), setOf(C)),
                    PossibleMove(listOf(4, 5, 6, 15, 16), setOf(C)),
                    PossibleMove(listOf(4, 5, 6, 7, 8, 17), setOf(D)),
                    PossibleMove(listOf(4, 5, 6, 7, 8, 17, 18), setOf(D))
                )
            ),
            Pair(
                5,
                listOf(
                    PossibleMove(listOf(4, 13), setOf(B)),
                    PossibleMove(listOf(4, 13, 14), setOf(B)),
                    PossibleMove(listOf(4, 3, 2, 11), setOf(A)),
                    PossibleMove(listOf(4, 3, 2, 11, 12), setOf(A)),
                    PossibleMove(listOf(6, 15), setOf(C)),
                    PossibleMove(listOf(6, 15, 16), setOf(C)),
                    PossibleMove(listOf(6, 7, 8, 17), setOf(D)),
                    PossibleMove(listOf(6, 7, 8, 17, 18), setOf(D))
                )
            ),
            Pair(
                7,
                listOf(
                    PossibleMove(listOf(6, 15), setOf(C)),
                    PossibleMove(listOf(6, 15, 16), setOf(C)),
                    PossibleMove(listOf(6, 5, 4, 13), setOf(B)),
                    PossibleMove(listOf(6, 5, 4, 13, 14), setOf(B)),
                    PossibleMove(listOf(6, 5, 4, 3, 2, 11), setOf(A)),
                    PossibleMove(listOf(6, 5, 4, 3, 2, 11, 12), setOf(A)),
                    PossibleMove(listOf(8, 17), setOf(D)),
                    PossibleMove(listOf(8, 17, 18), setOf(D))
                )
            ),
            Pair(
                9,
                listOf(
                    PossibleMove(listOf(8, 17), setOf(D)),
                    PossibleMove(listOf(8, 17, 18), setOf(D)),
                    PossibleMove(listOf(8, 7, 6, 15), setOf(C)),
                    PossibleMove(listOf(8, 7, 6, 15, 16), setOf(C)),
                    PossibleMove(listOf(8, 7, 6, 5, 4, 13), setOf(B)),
                    PossibleMove(listOf(8, 7, 6, 5, 4, 13, 15), setOf(B)),
                    PossibleMove(listOf(8, 7, 6, 5, 4, 3, 2, 11), setOf(A)),
                    PossibleMove(listOf(8, 7, 6, 5, 4, 3, 2, 11, 12), setOf(A))
                )
            ),
            Pair(10, emptyList()),
            Pair(
                11,
                listOf(
                    PossibleMove(listOf(12), setOf(A)),
                    PossibleMove(listOf(2, 1), allAmphipodTypes),
                    PossibleMove(listOf(2, 3), allAmphipodTypes),
                    PossibleMove(listOf(2, 3, 4, 5), allAmphipodTypes),
                    PossibleMove(listOf(2, 3, 4, 5, 6, 7), allAmphipodTypes),
                    PossibleMove(listOf(2, 3, 4, 5, 6, 7, 8, 9), allAmphipodTypes)
                )
            ),
            Pair(
                12,
                listOf(
                    PossibleMove(listOf(11, 2, 1), setOf(B, C, D)),
                    PossibleMove(listOf(11, 2, 3), setOf(B, C, D)),
                    PossibleMove(listOf(11, 2, 3, 4, 5), setOf(B, C, D)),
                    PossibleMove(listOf(11, 2, 3, 4, 5, 6, 7), setOf(B, C, D)),
                    PossibleMove(listOf(11, 2, 3, 4, 5, 6, 7, 8, 9), setOf(B, C, D))
                )
            ),
            Pair(
                13,
                listOf(
                    PossibleMove(listOf(14), setOf(B)),
                    PossibleMove(listOf(4, 3), allAmphipodTypes),
                    PossibleMove(listOf(4, 3, 2, 1), allAmphipodTypes),
                    PossibleMove(listOf(4, 5), allAmphipodTypes),
                    PossibleMove(listOf(4, 5, 6, 7), allAmphipodTypes),
                    PossibleMove(listOf(4, 5, 6, 7, 8, 9), allAmphipodTypes)
                )
            ),
            Pair(14,
                listOf(
                    PossibleMove(listOf(13, 4, 3), setOf(A, C, D)),
                    PossibleMove(listOf(13, 4, 3, 2, 1), setOf(A, C, D)),
                    PossibleMove(listOf(13, 4, 5), setOf(A, C, D)),
                    PossibleMove(listOf(13, 4, 5, 6, 7), setOf(A, C, D)),
                    PossibleMove(listOf(13, 4, 5, 6, 7, 8, 9), setOf(A, C, D))
                )
            ),
            Pair(
                15,
                listOf(
                    PossibleMove(listOf(16), setOf(C)),
                    PossibleMove(listOf(6, 5), allAmphipodTypes),
                    PossibleMove(listOf(6, 5, 4, 3), allAmphipodTypes),
                    PossibleMove(listOf(6, 5, 4, 3, 2, 1), allAmphipodTypes),
                    PossibleMove(listOf(6, 7), allAmphipodTypes),
                    PossibleMove(listOf(6, 7, 8, 9), allAmphipodTypes)
                )
            ),
            Pair(16,
                listOf(
                    PossibleMove(listOf(15, 6, 5), setOf(A, B, D)),
                    PossibleMove(listOf(15, 6, 5, 4, 3), setOf(A, B, D)),
                    PossibleMove(listOf(15, 6, 5, 4, 3, 2, 1), setOf(A, B, D)),
                    PossibleMove(listOf(15, 6, 7), setOf(A, B, D)),
                    PossibleMove(listOf(15, 6, 7, 8, 9), setOf(A, B, D))
                )
            ),
            Pair(
                17,
                listOf(
                    PossibleMove(listOf(18), setOf(D)),
                    PossibleMove(listOf(8, 7), allAmphipodTypes),
                    PossibleMove(listOf(8, 7, 6, 5), allAmphipodTypes),
                    PossibleMove(listOf(8, 7, 6, 5, 4, 3), allAmphipodTypes),
                    PossibleMove(listOf(8, 7, 6, 5, 4, 3, 2, 1), allAmphipodTypes),
                    PossibleMove(listOf(8, 9), allAmphipodTypes)
                )
            ),
            Pair(18,
                listOf(
                    PossibleMove(listOf(17, 8, 7), setOf(A, B, C)),
                    PossibleMove(listOf(17, 8, 7, 6, 5), setOf(A, B, C)),
                    PossibleMove(listOf(17, 8, 7, 6, 5, 4, 3), setOf(A, B, C)),
                    PossibleMove(listOf(17, 8, 7, 6, 5, 4, 3, 2, 1), setOf(A, B, C)),
                    PossibleMove(listOf(17, 8, 9), setOf(A, B, C))
                )
            )
        )

        val cells = createCells()

        /**
         * #############
         * #01234567890#
         * ###1#3#5#7###
         *   #2#4#6#8#
         *   #########
         */
        fun createCells(): List<Cell> {
            val cells = listOf(
                Cell(0, CellType.HALL_OPEN),
                Cell(1, CellType.HALL_OPEN),
                Cell(2, CellType.HALL_ABOVE_ROOM),
                Cell(3, CellType.HALL_OPEN),
                Cell(4, CellType.HALL_ABOVE_ROOM),
                Cell(5, CellType.HALL_OPEN),
                Cell(6, CellType.HALL_ABOVE_ROOM),
                Cell(7, CellType.HALL_OPEN),
                Cell(8, CellType.HALL_ABOVE_ROOM),
                Cell(9, CellType.HALL_OPEN),
                Cell(10, CellType.HALL_OPEN),
                Cell(11, CellType.ROOM_A),
                Cell(12, CellType.ROOM_A),
                Cell(13, CellType.ROOM_B),
                Cell(14, CellType.ROOM_B),
                Cell(15, CellType.ROOM_C),
                Cell(16, CellType.ROOM_C),
                Cell(17, CellType.ROOM_D),
                Cell(18, CellType.ROOM_D)
            )

            for (i in 0..9) cells[i].neighbors[Direction.RIGHT] = cells[i + 1]
            for (i in 1..10) cells[i].neighbors[Direction.LEFT] = cells[i - 1]

            cells[2].neighbors[Direction.DOWN] = cells[11]
            cells[11].neighbors[Direction.DOWN] = cells[12]
            cells[11].neighbors[Direction.UP] = cells[2]
            cells[12].neighbors[Direction.UP] = cells[11]

            cells[4].neighbors[Direction.DOWN] = cells[13]
            cells[13].neighbors[Direction.DOWN] = cells[14]
            cells[13].neighbors[Direction.UP] = cells[4]
            cells[14].neighbors[Direction.UP] = cells[13]

            cells[6].neighbors[Direction.DOWN] = cells[15]
            cells[15].neighbors[Direction.DOWN] = cells[16]
            cells[15].neighbors[Direction.UP] = cells[6]
            cells[16].neighbors[Direction.UP] = cells[15]

            cells[8].neighbors[Direction.DOWN] = cells[17]
            cells[17].neighbors[Direction.DOWN] = cells[18]
            cells[17].neighbors[Direction.UP] = cells[8]
            cells[18].neighbors[Direction.UP] = cells[17]

            return cells
        }
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

        val occupiedCellIndexes = amphipods.map { amphipod -> amphipod.cell }.toSet()

        val amphipodsByCell =
            amphipods.groupBy { amphipod -> amphipod.cell }.mapValues { (_, list) -> list.first() }

        fun isDone(): Boolean {
            return amphipods.all { amphipod -> amphipod.type.roomCellType == cells[amphipod.cell].type }
        }

        fun getPossibleMoves(): List<Move> {
            return amphipods
                .flatMap { amphipod ->
                    if (isOnTarget(amphipod)) emptyList()
                    else moves[amphipod.cell]!!
                        .filter { move ->
                            move.permittedAmphipodTypes.contains(amphipod.type)
                                    && move.steps.all { step -> !occupiedCellIndexes.contains(step) }
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

        fun move(amphipod: Amphipod, cell: Int): Situation {
            val movedAmphipod = amphipod.at(cell)
            val newAmphipods = LinkedHashSet(amphipods)
            newAmphipods.remove(amphipod)
            newAmphipods.add(movedAmphipod)
            return Situation(newAmphipods)
        }

        /**
         * #############
         * #01234567890#
         * ###1#3#5#7###
         *   #2#4#6#8#
         *   #########
         */
        fun print() {
            println("#############")

            print("#")
            for (i in (0..10)) printCell(i)
            println("#")

            print("###")
            printCell(11)
            print("#")
            printCell(13)
            print("#")
            printCell(15)
            print("#")
            printCell(17)
            println("###")

            print("  #")
            printCell(12)
            print("#")
            printCell(14)
            print("#")
            printCell(16)
            print("#")
            printCell(18)
            println("#")

            println("  #########")
        }

        override fun equals(other: Any?): Boolean {
            return other is Situation && key == other.key
        }

        override fun hashCode(): Int {
            return key.hashCode()
        }

        private fun isOnTarget(amphipod: Amphipod): Boolean {
            return when (amphipod.type) {
                A -> amphipod.cell == 12 || (amphipod.cell == 11 && amphipodsByCell[12]?.type == A)
                B -> amphipod.cell == 14 || (amphipod.cell == 13 && amphipodsByCell[14]?.type == B)
                C -> amphipod.cell == 16 || (amphipod.cell == 15 && amphipodsByCell[16]?.type == C)
                D -> amphipod.cell == 18 || (amphipod.cell == 17 && amphipodsByCell[18]?.type == D)
            }
        }

        private fun printCell(index: Int) {
            val amphipod = amphipodsByCell[index]
            if (amphipod != null) {
                print(amphipod.type)
            } else {
                print(".")
            }
        }
    }

    data class Cell(
        val index: Int,
        val type: CellType,
        val neighbors: MutableMap<Direction, Cell> = EnumMap(Direction::class.java)
    ) {
        override fun equals(other: Any?): Boolean {
            return index == (other as Cell).index
        }

        override fun hashCode(): Int {
            return index + 1
        }

        override fun toString(): String {
            return "$index"
        }
    }

    enum class CellType {

        HALL_OPEN,
        HALL_ABOVE_ROOM,
        ROOM_A,
        ROOM_B,
        ROOM_C,
        ROOM_D
    }

    data class Amphipod(val index: Int, val type: AmphipodType, val cell: Int) {

        fun at(newCell: Int): Amphipod {
            return Amphipod(index, type, newCell)
        }

        override fun toString(): String {
            return "$index,$type,${cell}"
        }
    }

    enum class AmphipodType(val roomCellType: CellType, val moveEnergy: Int) {

        A(CellType.ROOM_A, 1),
        B(CellType.ROOM_B, 10),
        C(CellType.ROOM_C, 100),
        D(CellType.ROOM_D, 1000)
    }

    enum class Direction {

        LEFT, UP, RIGHT, DOWN
    }

    data class Move(val amphipod: Amphipod, val cell: Int, val energy: Int)

    data class PossibleMove(val steps: List<Int>, val permittedAmphipodTypes: Set<AmphipodType>)

    data class SituationKey(val positions: Set<Pair<AmphipodType, Int>>)
}