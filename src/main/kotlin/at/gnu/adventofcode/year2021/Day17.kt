package at.gnu.adventofcode.year2021

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class Day17(targetAreaFromInput: String) {

    companion object {
        const val input = "/adventofcode/year2021/Day17.txt"
        private val targetAreaCommand = """target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""".toRegex()
    }

    data class Area(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

    private val targetArea: Area
    private val xRange: IntRange
    private val yRange: IntRange

    init {
        val (x1, x2, y1, y2) = targetAreaCommand.matchEntire(targetAreaFromInput)!!.destructured
        targetArea = Area(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        xRange = min(-abs(targetArea.x1), -abs(targetArea.x2))..max(abs(targetArea.x1), abs(targetArea.x2))
        yRange = min(-abs(targetArea.y1), -abs(targetArea.y2))..max(abs(targetArea.y1), abs(targetArea.y2))
    }

    fun part1(): Int =
        simulateShootings().maxOf { it }

    fun part2(): Int =
        simulateShootings().filter { it >= 0 }.size

    private fun simulateShootings(): List<Int> =
        xRange.fold(listOf()) { acc, x -> acc + yRange.fold(listOf()) { acc2, y -> acc2 + shoot(x, y) } }

    private fun shoot(initialX: Int, initialY: Int): Int {
        var maxHeight = 0
        var pX = 0
        var pY = 0
        var dX = initialX
        var dY = initialY
        while(true) {
            pX += dX
            pY += dY
            maxHeight = max(maxHeight, pY)
            if ((pX in targetArea.x1..targetArea.x2) && (pY in targetArea.y1..targetArea.y2))
                return maxHeight
            else if ((pX >= targetArea.x2) || (pY < targetArea.y1))
                return -1
            dY--
            dX -= dX.sign
        }
    }
}

fun main() {
    val day17 = Day17(Day17::class.java.getResource(Day17.input)!!.readText().trim())
    println("Day17::part1 = ${day17.part1()}")
    println("Day17::part2 = ${day17.part2()}")
}
