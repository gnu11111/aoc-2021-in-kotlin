package at.gnu.algorithms

import kotlin.math.sqrt

class AStarAlgorithm(private val field: List<List<Node>>) {

    companion object {
        val outOfBounds = Node(-1, -1, Int.MAX_VALUE)
    }

    data class Node(val x: Int, val y: Int, val cost: Int, var f: Double = 0.0, var g: Int = 0, var prev: Node? = null)

    private val openList = mutableListOf<Node>()
    private val closedList = mutableSetOf<Node>()

    fun cheapestPath(from: Node, to: Node): Int {
        openList.add(from)
        while (openList.isNotEmpty()) {
            val currentNode = openList.minByOrNull { it.f }!!
            openList -= currentNode
            if (currentNode === to)
                return currentNode.g
            closedList += currentNode
            currentNode.expand(to)
        }
        return -1
    }

    private fun Node.expand(to: Node) {
        for (successor in neighbors()) {
            if (successor in closedList)
                continue
            val tentativeG = this.g + successor.cost
            if ((successor in openList) && (tentativeG >= successor.g))
                continue
            successor.prev = this
            successor.g = tentativeG
            val f = tentativeG + successor.h(to)
            successor.f = f
            if (successor !in openList)
                openList += successor
        }
    }

    private fun Node.neighbors(): Set<Node> =
        setOf(nodeAt(x, y + 1), nodeAt(x + 1, y), nodeAt(x - 1, y), nodeAt(x, y - 1))

    private fun nodeAt(x: Int, y: Int): Node =
        field.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds

    private fun Node.h(to: Node): Double =
        sqrt((((this.x - to.x) * (this.x - to.x)) + ((this.y - to.y) * (this.y - to.y))).toDouble())
}

fun main() {
    val input = listOf(
        listOf(1, 1, 6, 3, 7, 5, 1, 7, 4, 2),
        listOf(1, 3, 8, 1, 3, 7, 3, 6, 7, 2),
        listOf(2, 1, 3, 6, 5, 1, 1, 3, 2, 8),
        listOf(3, 6, 9, 4, 9, 3, 1, 5, 6, 9),
        listOf(7, 4, 6, 3, 4, 1, 7, 1, 1, 1),
        listOf(1, 3, 1, 9, 1, 2, 8, 1, 3, 7),
        listOf(1, 3, 5, 9, 9, 1, 2, 4, 2, 1),
        listOf(3, 1, 2, 5, 4, 2, 1, 6, 3, 9),
        listOf(1, 2, 9, 3, 1, 3, 8, 5, 2, 1),
        listOf(2, 3, 1, 1, 9, 4, 4, 5, 8, 1),
    )
    val field = input.mapIndexed { y, line ->
        line.mapIndexed { x, cost ->
            if (cost > 0) AStarAlgorithm.Node(x, y, cost) else AStarAlgorithm.outOfBounds
        }
    }
    val from = field.first().first()
    val to = field.last().last()
    val minCost = AStarAlgorithm(field).cheapestPath(from, to)
    if (minCost >= 0)
        println("The cheapest path from ${from.x}/${from.y} to ${to.x}/${to.y} costs $minCost")
    else
        println("Destination ${to.x}/${to.y} cannot be reached from source ${from.x}/${from.y}")

    field.visualizePath(to)
}

private fun List<List<AStarAlgorithm.Node>>.visualizePath(to: AStarAlgorithm.Node) {
    val smallNumbers = "₀₁₂₃₄₅₆₇₈₉"
    val nodes = mutableListOf(to)
    var current = to.prev
    while (current != null) {
        nodes.add(current)
        current = current.prev
    }
    for (y in this.indices) {
        for (x in this[y].indices) {
            val node = this[y][x]
            if (nodes.contains(node))
                print(node.cost)
            else
                print(smallNumbers[node.cost])
        }
        println("")
    }
}
