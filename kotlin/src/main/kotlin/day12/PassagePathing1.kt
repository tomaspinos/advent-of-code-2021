package day12

import Common

fun main() {
    println(PassagePathing1().paths(Common.readInput("/day12/input.txt")))
}

class PassagePathing1 {

    private val startNode: Node = Node("start")

    private val endNode: Node = Node("end")

    fun paths(input: List<String>): Int {
        val paths = generatePaths(Path(listOf(startNode)), readSteps(input))
        println("paths: $paths")
        return paths.size
    }

    private fun readSteps(input: List<String>): Steps {
        return Steps(
            input.flatMap { line ->
                val split = line.split('-')
                val from = Node(split[0])
                val to = Node(split[1])
                listOf(Step(from, to), Step(to, from))
            })
            .removeStepsLeadingTo(startNode)
            .removeStepsLeadingFrom(endNode)
    }

    private fun generatePaths(path: Path, steps: Steps): List<Path> {
        return steps.getPossibleSteps(path.lastNode())
            .flatMap { possibleStep ->
                if (possibleStep.to == endNode) {
                    listOf(path.append(possibleStep.to))
                } else {
                    val remainingSteps = if (possibleStep.to.canBeVisitedOnlyOnce) {
                        steps.removeStepsLeadingTo(possibleStep.to)
                    } else {
                        steps
                    }
                    generatePaths(path.append(possibleStep.to), remainingSteps)
                }
            }
    }

    data class Node(val name: String) {

        val canBeVisitedOnlyOnce: Boolean = name == name.lowercase()
    }

    data class Step(val from: Node, val to: Node) {

        override fun toString(): String {
            return "${from.name}->${to.name}"
        }
    }

    data class Steps(val steps: List<Step>) {

        fun getPossibleSteps(from: Node): List<Step> {
            return steps.filter { step -> step.from == from }
        }

        fun removeStepsLeadingTo(node: Node): Steps {
            return Steps(steps.filter { step -> step.to != node })
        }

        fun removeStepsLeadingFrom(node: Node): Steps {
            return Steps(steps.filter { step -> step.from != node })
        }
    }

    data class Path(val nodes: List<Node>) {

        fun append(node: Node): Path {
            val extendedNodes = ArrayList<Node>(nodes)
            extendedNodes.add(node)
            return Path(extendedNodes)
        }

        fun lastNode(): Node {
            return nodes.last()
        }

        override fun toString(): String {
            return nodes.joinToString("->") { node -> node.name }
        }
    }
}