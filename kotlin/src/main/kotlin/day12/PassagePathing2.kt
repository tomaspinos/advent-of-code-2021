package day12

import Common

fun main() {
    val input = Common.readInput("/day12/input.txt")
    val result = PassagePathing2().paths(input)
    println(result)
}

class PassagePathing2 {

    private val startNode: Node = Node("start")

    private val endNode: Node = Node("end")

    fun paths(input: List<String>): Int {
        val steps = readSteps(input)
        val startingPath = Path(listOf(startNode))
        val paths = generatePaths(startingPath, steps)
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
        println("generatePaths($path, $steps)")
        val possibleSteps = steps.getPossibleSteps(path.lastNode())
        val resultingPaths = ArrayList<Path>()
        for (possibleStep in possibleSteps) {
            if (possibleStep.to == endNode) {
                resultingPaths.add(path.append(possibleStep.to))
            } else if (path.canAppend(possibleStep.to)) {
                resultingPaths.addAll(generatePaths(path.append(possibleStep.to), steps))
            }
        }
        return resultingPaths
    }

    data class Node(val name: String) {

        val small: Boolean = name == name.lowercase()
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

        fun canAppend(newNode: Node): Boolean {
            return if (newNode.small) {
                val containsNewNode = nodes.any { node -> node == newNode }
                if (containsNewNode) {
                    val containsSmallNodeVisitedMoreThanOnce = nodes
                        .filter { node -> node.small }
                        .groupBy { node -> node }
                        .any { entry -> entry.value.size > 1 }
                    !containsSmallNodeVisitedMoreThanOnce
                } else {
                    true
                }
            } else {
                true
            }
        }

        override fun toString(): String {
            return nodes.joinToString("->") { node -> node.name }
        }
    }
}