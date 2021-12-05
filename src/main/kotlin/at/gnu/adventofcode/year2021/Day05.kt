package at.gnu.adventofcode.year2021

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day05(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day05.txt"
    }

    data class Coordinate(val x: Int, val y: Int)
    data class Line(val from: Coordinate, val to: Coordinate)
    private val lines: List<Line>
    init {
        val newLines = mutableListOf<Line>()
        for (line in input) {
            val (from, to) = line.split("->").map { it.split(",") }
                .map { Coordinate(it[0].trim().toInt(), it[1].trim().toInt()) }
            newLines.add(Line(from, to))
        }
        lines = newLines.toList()
    }


    fun part1(): Int {
        val coordinate = mutableMapOf<Coordinate, Int>()
        for (line in lines) {
            if ((line.from.x != line.to.x) && (line.from.y != line.to.y))
                continue
            if (line.from.x == line.to.x)
                for (y in min(line.from.y, line.to.y)..max(line.from.y, line.to.y)) {
                    val newCoordinate = Coordinate(line.from.x, y)
                    if (!coordinate.containsKey(newCoordinate))
                        coordinate[newCoordinate] = 1
                    else
                        coordinate[newCoordinate] = coordinate[newCoordinate]!! + 1
                }
            else
                for (x in min(line.from.x, line.to.x)..max(line.from.x, line.to.x)) {
                    val newCoordinate = Coordinate(x, line.from.y)
                    if (!coordinate.containsKey(newCoordinate))
                        coordinate[newCoordinate] = 1
                    else
                        coordinate[newCoordinate] = coordinate[newCoordinate]!! + 1
                }
        }
        return coordinate.values.count { it > 1 }
    }

    fun part2(): Int {
        val coordinates = mutableMapOf<Coordinate, Int>()
        for (line in lines) {
            if (line.from.x == line.to.x)
                for (y in min(line.from.y, line.to.y)..max(line.from.y, line.to.y)) {
                    val newCoordinate = Coordinate(line.from.x, y)
                    if (!coordinates.containsKey(newCoordinate))
                        coordinates[newCoordinate] = 1
                    else
                        coordinates[newCoordinate] = coordinates[newCoordinate]!! + 1
                }
            else if (line.from.y == line.to.y)
                for (x in min(line.from.x, line.to.x)..max(line.from.x, line.to.x)) {
                    val newCoordinate = Coordinate(x, line.from.y)
                    if (!coordinates.containsKey(newCoordinate))
                        coordinates[newCoordinate] = 1
                    else
                        coordinates[newCoordinate] = coordinates[newCoordinate]!! + 1
                }
            else
                for (i in 0..abs(line.from.x - line.to.x)) {
                    val dx = if (line.to.x > line.from.x) i else -i
                    val dy = if (line.to.y > line.from.y) i else -i
                    val newCoordinate = Coordinate(line.from.x + dx, line.from.y + dy)
                    if (!coordinates.containsKey(newCoordinate))
                        coordinates[newCoordinate] = 1
                    else
                        coordinates[newCoordinate] = coordinates[newCoordinate]!! + 1
                }
        }
        return coordinates.values.count { it > 1 }
    }

    @Suppress("unused")
    private fun showMap(coordinates: Map<Coordinate, Int>) {
        for (y in 0..9) {
            val line = StringBuilder()
            for (x in 0..9) {
                if (coordinates.containsKey(Coordinate(x, y)))
                    line.append(coordinates[Coordinate(x, y)])
                else
                    line.append(".")
            }
            println(line)
        }
    }
}

fun main() {
    val input = Day05::class.java.getResource(Day05.input)!!.readText().trim().split("\n", "\r\n")
    val day05 = Day05(input)
    println("Day05::part1 = ${day05.part1()}")
    println("Day05::part2 = ${day05.part2()}")
}
