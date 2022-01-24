package at.gnu.adventofcode.year2021

import kotlin.math.abs
import kotlin.math.max

class Day05(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day05.txt"
        const val dangerousAmount = 2
    }

    data class Coordinate(val x: Int, val y: Int)
    class Line(val from: Coordinate, val to: Coordinate)

    private val lines: List<Line>

    init {
        val linesFromInput = mutableListOf<Line>()
        for (line in input) {
            val (from, to) = line.split(" -> ").map { it.split(",") }.map { Coordinate(it[0].toInt(), it[1].toInt()) }
            linesFromInput.add(Line(from, to))
        }
        lines = linesFromInput.toList()
    }


    fun part1(): Int =
        calculateDangerousCoordinates()

    fun part2(): Int =
        calculateDangerousCoordinates(withDiagonals = true)

    private fun calculateDangerousCoordinates(withDiagonals: Boolean = false): Int {
        val coordinates = mutableMapOf<Coordinate, Int>()
        for (line in lines) {
            if (!withDiagonals && (line.from.x != line.to.x) && (line.from.y != line.to.y))
                continue
            val dx = line.to.x.compareTo(line.from.x)
            val dy = line.to.y.compareTo(line.from.y)
            val lineLength = max(abs(line.from.x - line.to.x), abs(line.from.y - line.to.y))
            for (i in 0..lineLength) {
                val coordinate = Coordinate(line.from.x + (dx * i), line.from.y + (dy * i))
                coordinates[coordinate] = (coordinates[coordinate] ?: 0) + 1
            }
        }
        return coordinates.values.count { it >= dangerousAmount }
    }
}

fun main() {
    val input = Day05::class.java.getResource(Day05.input)!!.readText().trim().split("\n", "\r\n")
    val day05 = Day05(input)
    println("Day05::part1 -> ${day05.part1()}")
    println("Day05::part2 -> ${day05.part2()}")
}
