package at.gnu.adventofcode.year2021

import kotlin.math.sqrt

class Day15(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day15.txt"
        val outOfBounds = Node(-1, -1, Int.MAX_VALUE)
    }

    class Node(val x: Int, val y: Int, val cost: Int, var f: Double = 0.0, var g: Int = 0, var prev: Node? = null)

    private val map = input.mapIndexed { y, line -> line.mapIndexed { x, level -> Node(x, y, level - '0') } }

    fun part1(): Int =
        safestPath(map.first().first(), map.last().last()) { x: Int, y: Int ->
            map.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
        }

    fun part2(): Int {
        val bigMap = map.multiplyBy(5)
        return safestPath(bigMap.first().first(), bigMap.last().last()) { x: Int, y: Int ->
            bigMap.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
        }
    }

    private fun List<List<Node>>.multiplyBy(scale: Int): List<List<Node>> {
        val map = mutableListOf<List<Node>>()
        for (yScale in 0 until scale)
            for (y in this.indices) {
                val line = mutableListOf<Node>()
                for (xScale in 0 until scale)
                    for (x in this[y].indices)
                        line.add(Node((this.first().size * xScale) + x, (this.size * yScale) + y,
                            ((this[y][x].cost + yScale + xScale - 1) % 9) + 1))
                map.add(line.toList())
            }
        return map.toList()
    }

    private fun safestPath(from: Node, to: Node, nodeAt: (Int, Int) -> Node): Int {
        val openList = mutableListOf(from)
        val closedList = mutableSetOf<Node>()
        while (openList.isNotEmpty()) {
            val currentNode = openList.minByOrNull { it.f }!!
            openList -= currentNode
            if (currentNode === to)
                return currentNode.g
            closedList += currentNode
            currentNode.expand(to, openList, closedList, nodeAt)
        }
        return -1
    }

    private fun Node.expand(to: Node, openList: MutableList<Node>, closedList: Set<Node>, nodeAt: (Int, Int) -> Node) {
        for (successor in neighbors(nodeAt)) {
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

    private fun Node.neighbors(nodeAt: (Int, Int) -> Node): Set<Node> =
        setOf(nodeAt(x, y + 1), nodeAt(x + 1, y), nodeAt(x - 1, y), nodeAt(x, y - 1))

    private fun Node.h(to: Node): Double =
        sqrt((((this.x - to.x) * (this.x - to.x)) + ((this.y - to.y) * (this.y - to.y))).toDouble())
}

fun main() {
    val input = Day15::class.java.getResource(Day15.input)!!.readText().trim().split("\n", "\r\n")
    val day15 = Day15(input)
    println("Day15::part1 -> ${day15.part1()}")
    println("Day15::part2 -> ${day15.part2()}")
}
