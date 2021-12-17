package at.gnu.adventofcode.year2021

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day17(targetAreaFromInput: String) {

    companion object {
        const val input = "/adventofcode/year2021/Day17.txt"
        private val targetAreaCommand = """target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""".toRegex()
    }

    data class Area(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

    private val targetArea: Area

    init {
        val (x1, x2, y1, y2) = targetAreaCommand.matchEntire(targetAreaFromInput)!!.destructured
        targetArea = Area(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
    }

    fun part1(): Int {
        var maxHeight = 0
        for (x in min(-abs(targetArea.x1), -abs(targetArea.x2))..max(abs(targetArea.x1), abs(targetArea.x2)))
            for (y in min(-abs(targetArea.y1), -abs(targetArea.y2))..max(abs(targetArea.y1), abs(targetArea.y2)))
                maxHeight = max(maxHeight, shoot(x, y))
        return maxHeight
    }

    fun part2(): Int {
        val hits = mutableSetOf<Pair<Int, Int>>()
        for (x in min(-abs(targetArea.x1), -abs(targetArea.x2))..max(abs(targetArea.x1), abs(targetArea.x2)))
            for (y in min(-abs(targetArea.y1), -abs(targetArea.y2))..max(abs(targetArea.y1), abs(targetArea.y2)))
                if (shoot(x, y) >= 0) hits += Pair(x, y)
        return hits.size
    }

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
            if (dX > 0) dX-- else if (dX < 0) dX++
        }
    }
}

fun main() {
    val day17 = Day17(Day17::class.java.getResource(Day17.input)!!.readText().trim())
    println("Day17::part1 = ${day17.part1()}")
    println("Day17::part2 = ${day17.part2()}")
}
