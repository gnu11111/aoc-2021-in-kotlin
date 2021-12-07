package at.gnu.adventofcode.year2021

import kotlin.math.abs
import kotlin.math.min

class Day07(input: List<Int>) {

    companion object {
        const val input = "/adventofcode/year2021/Day07.txt"
    }

    private val crabPositions: List<Int> = input.sorted()

    fun part1(): Int {
        val median = crabPositions[crabPositions.size / 2]
        return crabPositions.sumOf { abs(it - median) }
    }

    fun part2(): Int {
        val medium = crabPositions.sum() / crabPositions.size
        return min(sumOfFuelCosts(medium), sumOfFuelCosts(medium + 1))
    }

    private fun sumOfFuelCosts(medium: Int) =
        crabPositions.sumOf { (abs(it - medium) * (abs(it - medium) + 1)) / 2 }
}

fun main() {
    val input = Day07::class.java.getResource(Day07.input)!!.readText().trim().split(",").map { it.toInt() }
    val day07 = Day07(input)
    println("Day07::part1 -> ${day07.part1()}")
    println("Day07::part2 -> ${day07.part2()}")
}
