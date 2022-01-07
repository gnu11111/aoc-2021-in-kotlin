package at.gnu.adventofcode.year2021

import java.util.*
import kotlin.math.sqrt

class Day15(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day15.txt"
        val outOfBounds = Position(-1, -1, Int.MAX_VALUE)
    }

    class Position(val x: Int, val y: Int, val risk: Int, var f: Double = 0.0, var g: Int = 0,
                   var predecessor: Position? = null) : Comparable<Position> {
        override fun compareTo(other: Position): Int =
            this.f.compareTo(other.f)
    }

    private val map = input.mapIndexed { y, line -> line.mapIndexed { x, risk -> Position(x, y, risk.digitToInt()) } }

    fun part1(): Int =
        calculateRiskOfSafestPath(map.first().first(), map.last().last()) { x: Int, y: Int ->
            map.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
        }

    fun part2(): Int {
        val bigMap = map.scaleBy(5)
        return calculateRiskOfSafestPath(bigMap.first().first(), bigMap.last().last(), ::aStar) { x: Int, y: Int ->
            bigMap.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
        }
    }

    private fun calculateRiskOfSafestPath(from: Position, to: Position, h: (Position, Position) -> Double = ::dijkstra,
                                          positionAt: (Int, Int) -> Position): Int {
        val openList = PriorityQueue<Position>().apply { add(from) }
        val closedList = mutableSetOf<Position>()
        while (openList.isNotEmpty()) {
            val currentPosition = openList.poll()
            if (currentPosition === to)
                return currentPosition.g
            closedList += currentPosition
            currentPosition.expand(to, openList, closedList, h, positionAt)
        }
        return -1
    }

    private fun Position.expand(to: Position, openList: PriorityQueue<Position>, closedList: Set<Position>,
                                h: (Position, Position) -> Double, positionAt: (Int, Int) -> Position) {
        for (successor in this.neighbors(positionAt)) {
            if (successor in closedList)
                continue
            val tentativeG = this.g + successor.risk
            if ((successor in openList) && (tentativeG >= successor.g))
                continue
            successor.predecessor = this
            successor.g = tentativeG
            successor.f = tentativeG + h(successor, to)
            if (successor !in openList)
                openList += successor
        }
    }

    private fun List<List<Position>>.scaleBy(scaleFactor: Int): List<List<Position>> {
        val map = mutableListOf<List<Position>>()
        for (yScale in 0 until scaleFactor)
            for (y in this.indices) {
                val row = mutableListOf<Position>()
                for (xScale in 0 until scaleFactor)
                    for (x in this[y].indices)
                        row.add(Position((this.first().size * xScale) + x, (this.size * yScale) + y,
                            ((this[y][x].risk + yScale + xScale - 1) % 9) + 1))
                map.add(row.toList())
            }
        return map.toList()
    }

    private fun Position.neighbors(positionAt: (Int, Int) -> Position): Set<Position> =
        setOf(positionAt(x, y + 1), positionAt(x + 1, y), positionAt(x - 1, y), positionAt(x, y - 1))

    @Suppress("UNUSED_PARAMETER")
    private fun dijkstra(from: Position, to: Position): Double = 0.0

    private fun aStar(from: Position, to: Position): Double =
        sqrt((((from.x - to.x) * (from.x - to.x)) + ((from.y - to.y) * (from.y - to.y))).toDouble())
}

fun main() {
    val input = Day15::class.java.getResource(Day15.input)!!.readText().trim().split("\n", "\r\n")
    val day15 = Day15(input)
    println("Day15::part1 -> ${day15.part1()}")
    println("Day15::part2 -> ${day15.part2()}")
}
