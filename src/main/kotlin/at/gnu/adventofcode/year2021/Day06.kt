package at.gnu.adventofcode.year2021

import java.util.Collections.rotate

class Day06(input: List<Int>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day06.txt"
        const val NUMBER_OF_DAYS_TILL_REPRODUCTION = 8
    }

    private val reproductionTimers: List<Long>

    init {
        val timersFromInput = MutableList(NUMBER_OF_DAYS_TILL_REPRODUCTION + 1) { 0L }
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
            processTimers[NUMBER_OF_DAYS_TILL_REPRODUCTION - 2] += processTimers[NUMBER_OF_DAYS_TILL_REPRODUCTION]
        }
        return processTimers
    }

    private fun List<Long>.calculateAmountOfLanternfish(): Long =
        this.sum()
}

fun main() {
    val input = Day06::class.java.getResource(Day06.RESOURCE)!!.readText().trim().split(",").map { it.toInt() }
    val day06 = Day06(input)
    println("Day06::part1 -> ${day06.part1()}")
    println("Day06::part2 -> ${day06.part2()}")
}
