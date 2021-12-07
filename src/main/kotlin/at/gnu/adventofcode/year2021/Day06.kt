package at.gnu.adventofcode.year2021

import java.util.Collections.rotate

class Day06(input: List<Int>) {

    companion object {
        const val input = "/adventofcode/year2021/Day06.txt"
        const val numberOfDaysTillReproduction = 8
    }

    private val reproductionTimers: List<Long>

    init {
        val timersFromInput = MutableList(numberOfDaysTillReproduction + 1) { 0L }
        for (timer in input)
            timersFromInput[timer]++
        reproductionTimers = timersFromInput.toList()
    }

    fun part1(): Long =
        ageLanternfishPopulation(80).calculateAmountOfLanternfish()

    fun part2(): Long =
        ageLanternfishPopulation(256).calculateAmountOfLanternfish()

    private fun ageLanternfishPopulation(days: Int): List<Long> {
        val processTimers = reproductionTimers.toMutableList()
        repeat(days) {
            rotate(processTimers, -1)
            processTimers[numberOfDaysTillReproduction - 2] += processTimers[numberOfDaysTillReproduction]
        }
        return processTimers
    }

    private fun List<Long>.calculateAmountOfLanternfish(): Long =
        this.sum()
}

fun main() {
    val input = Day06::class.java.getResource(Day06.input)!!.readText().trim().split(",").map { it.toInt() }
    val day06 = Day06(input)
    println("Day06::part1 -> ${day06.part1()}")
    println("Day06::part2 -> ${day06.part2()}")
}
