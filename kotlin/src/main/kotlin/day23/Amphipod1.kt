package day23

import java.util.*

fun main() {
//    println(Amphipod1().move())
}

class Amphipod1 {

    val allAmphipodTypes = AmphipodType.values().toSet()

    /**
     * #############
     * #01234567890#
     * ###1#3#5#7###
     *   #2#4#6#8#
     *   #########
     */
    val moves = mapOf(
        Pair(0, listOf(PossibleMove(listOf(1), allAmphipodTypes))),
        Pair(
            1,
            listOf(
                PossibleMove(listOf(0), allAmphipodTypes),
                PossibleMove(listOf(2, 3), allAmphipodTypes),
                PossibleMove(listOf(2, 11), setOf(AmphipodType.A))
            )
        ),
        Pair(
            3,
            listOf(
                PossibleMove(listOf(2, 1), allAmphipodTypes),
                PossibleMove(listOf(4, 5), allAmphipodTypes),
                PossibleMove(listOf(2, 11), setOf(AmphipodType.A)),
                PossibleMove(listOf(4, 13), setOf(AmphipodType.B))
            )
        ),
        Pair(
            5,
            listOf(
                PossibleMove(listOf(4, 3), allAmphipodTypes),
                PossibleMove(listOf(6, 7), allAmphipodTypes),
                PossibleMove(listOf(4, 13), setOf(AmphipodType.B)),
                PossibleMove(listOf(6, 15), setOf(AmphipodType.C))
            )
        ),
        Pair(
            7,
            listOf(
                PossibleMove(listOf(6, 5), allAmphipodTypes),
                PossibleMove(listOf(8, 9), allAmphipodTypes),
                PossibleMove(listOf(6, 15), setOf(AmphipodType.C)),
                PossibleMove(listOf(8, 17), setOf(AmphipodType.D))
            )
        ),
        Pair(
            9,
            listOf(
                PossibleMove(listOf(8, 7), allAmphipodTypes),
                PossibleMove(listOf(8, 17), setOf(AmphipodType.D)),
                PossibleMove(listOf(10), allAmphipodTypes)
            )
        ),
        Pair(10, listOf(PossibleMove(listOf(9), allAmphipodTypes))),
        Pair(
            11,
            listOf(
                PossibleMove(listOf(2, 1), allAmphipodTypes),
                PossibleMove(listOf(2, 3), allAmphipodTypes),
                PossibleMove(listOf(12), setOf(AmphipodType.A))
            )
        ),
        Pair(12, listOf(PossibleMove(listOf(11), allAmphipodTypes))),
        Pair(
            13,
            listOf(
                PossibleMove(listOf(4, 3), allAmphipodTypes),
                PossibleMove(listOf(4, 5), allAmphipodTypes),
                PossibleMove(listOf(14), setOf(AmphipodType.B))
            )
        ),
        Pair(14, listOf(PossibleMove(listOf(13), allAmphipodTypes))),
        Pair(
            15,
            listOf(
                PossibleMove(listOf(6, 5), allAmphipodTypes),
                PossibleMove(listOf(6, 7), allAmphipodTypes),
                PossibleMove(listOf(16), setOf(AmphipodType.C))
            )
        ),
        Pair(16, listOf(PossibleMove(listOf(15), allAmphipodTypes))),
        Pair(
            17,
            listOf(
                PossibleMove(listOf(8, 7), allAmphipodTypes),
                PossibleMove(listOf(8, 9), allAmphipodTypes),
                PossibleMove(listOf(18), setOf(AmphipodType.D))
            )
        ),
        Pair(18, listOf(PossibleMove(listOf(17), allAmphipodTypes)))
    )

    val cells = createCells()

    fun move(
        situation: Situation,
        history: HashSet<Set<Amphipod>>,
        resultCache: HashMap<Set<Amphipod>, Int>,
        depth: Int
    ): Int {
        situation.print()
        println()

        if (situation.isDone()) {
            return situation.energy
        }

        if (resultCache.contains(situation.amphipods)) {
            return resultCache[situation.amphipods]!!
        }

        if (history.contains(situation.amphipods)) {
            return Int.MAX_VALUE
        }

        if (depth > 50) {
            println("Too deep")
            return Int.MAX_VALUE
        }

        history.add(situation.amphipods)

        var leastEnergy = Int.MAX_VALUE

        for (amphipod in situation.amphipods) {
            val possibleMoves = situation.getPossibleMoves(amphipod)
            for (possibleMove in possibleMoves) {
                val newSituation = situation.move(amphipod, possibleMove.cell, possibleMove.energy)
                val energy = move(newSituation, history, resultCache, depth + 1)
                if (energy < leastEnergy) {
                    leastEnergy = energy
                }
            }
        }

        resultCache[situation.amphipods] = leastEnergy

        return leastEnergy
    }

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

    data class Situation(
        val cells: List<Cell>,
        val moves: Map<Int, List<PossibleMove>>,
        val amphipods: Set<Amphipod>,
        val energy: Int
    ) {

        fun isDone(): Boolean {
            return amphipods.all { amphipod -> amphipod.type.roomCellType == amphipod.cell.type }
        }

        fun getPossibleMoves(amphipod: Amphipod): List<Move> {
            if (isOnTarget(amphipod)) return emptyList()

            val result = ArrayList<Move>()

            for (move in moves[amphipod.cell.index]!!) {
                val canMove = move.permittedAmphipodTypes.contains(amphipod.type)
                        && move.steps.all { step -> isCellFree(cells[step]) }
                if (canMove) {
                    result.add(Move(cells[move.steps.last()], amphipod.type.moveEnergy * move.steps.size))
                }
            }

            return result
        }

        fun move(amphipod: Amphipod, cell: Cell, moveEnergy: Int): Situation {
            val movedAmphipod = amphipod.at(cell)
            val newAmphipods = LinkedHashSet(amphipods)
            newAmphipods.remove(amphipod)
            newAmphipods.add(movedAmphipod)
            return Situation(cells, moves, newAmphipods, energy + moveEnergy)
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

        private fun isOnTarget(amphipod: Amphipod): Boolean {
            return when (amphipod.type) {
                AmphipodType.A -> amphipod.cell.index == 12 || (amphipod.cell.index == 11 && getAmphipodByCell(12)?.type == AmphipodType.A)
                AmphipodType.B -> amphipod.cell.index == 14 || (amphipod.cell.index == 13 && getAmphipodByCell(14)?.type == AmphipodType.B)
                AmphipodType.C -> amphipod.cell.index == 16 || (amphipod.cell.index == 15 && getAmphipodByCell(16)?.type == AmphipodType.C)
                AmphipodType.D -> amphipod.cell.index == 18 || (amphipod.cell.index == 17 && getAmphipodByCell(18)?.type == AmphipodType.D)
            }
        }

        private fun isCellFree(cell: Cell): Boolean {
            return amphipods.none { amphipod -> amphipod.cell == cell }
        }

        private fun getAmphipodByCell(index: Int): Amphipod? {
            return amphipods.firstOrNull { amphipod -> amphipod.cell.index == index }
        }

        private fun printCell(index: Int) {
            val amphipod = getAmphipodByCell(index)
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

    data class Amphipod(val index: Int, val type: AmphipodType, val cell: Cell) {

        fun at(newCell: Cell): Amphipod {
            return Amphipod(index, type, newCell)
        }

        override fun toString(): String {
            return "$index,$type,${cell.index}"
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

    data class Move(val cell: Cell, val energy: Int)

    data class PossibleMove(val steps: List<Int>, val permittedAmphipodTypes: Set<AmphipodType>)
}