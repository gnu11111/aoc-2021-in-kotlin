package at.gnu.adventofcode.year2021

import kotlin.math.sqrt

class Day15(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day15.txt"
        val outOfBounds = Position(-1, -1, Int.MAX_VALUE)
    }

    class Position(val x: Int, val y: Int, val risk: Int, var f: Double = 0.0, var g: Int = 0,
                   var predecessor: Position? = null)

    private val map = input.mapIndexed { y, line -> line.mapIndexed { x, level -> Position(x, y, level - '0') } }

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

    private fun safestPath(from: Position, to: Position, positionAt: (Int, Int) -> Position): Int {
        val openList = mutableListOf(from)
        val closedList = mutableSetOf<Position>()
        while (openList.isNotEmpty()) {
            val currentPosition = openList.minByOrNull { it.f }!!
            openList -= currentPosition
            if (currentPosition === to)
                return currentPosition.g
            closedList += currentPosition
            currentPosition.expand(to, openList, closedList, positionAt)
        }
        return -1
    }

    private fun Position.expand(to: Position, openList: MutableList<Position>, closedList: Set<Position>,
                                positionAt: (Int, Int) -> Position) {
        for (successor in this.neighbors(positionAt)) {
            if (successor in closedList)
                continue
            val tentativeG = this.g + successor.risk
            if ((successor in openList) && (tentativeG >= successor.g))
                continue
            successor.predecessor = this
            successor.g = tentativeG
            val f = tentativeG + successor.h(to)
            successor.f = f
            if (successor !in openList)
                openList += successor
        }
    }

    private fun List<List<Position>>.multiplyBy(scale: Int): List<List<Position>> {
        val map = mutableListOf<List<Position>>()
        for (yScale in 0 until scale)
            for (y in this.indices) {
                val line = mutableListOf<Position>()
                for (xScale in 0 until scale)
                    for (x in this[y].indices)
                        line.add(Position((this.first().size * xScale) + x, (this.size * yScale) + y,
                            ((this[y][x].risk + yScale + xScale - 1) % 9) + 1))
                map.add(line.toList())
            }
        return map.toList()
    }

    private fun Position.neighbors(positionAt: (Int, Int) -> Position): Set<Position> =
        setOf(positionAt(x, y + 1), positionAt(x + 1, y), positionAt(x - 1, y), positionAt(x, y - 1))

    private fun Position.h(to: Position): Double =
        sqrt((((this.x - to.x) * (this.x - to.x)) + ((this.y - to.y) * (this.y - to.y))).toDouble())
}

fun main() {
    val input = Day15::class.java.getResource(Day15.input)!!.readText().trim().split("\n", "\r\n")
    val day15 = Day15(input)
    println("Day15::part1 -> ${day15.part1()}")
    println("Day15::part2 -> ${day15.part2()}")
}
