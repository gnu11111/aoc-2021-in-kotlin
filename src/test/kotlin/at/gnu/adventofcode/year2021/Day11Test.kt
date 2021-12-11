package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day11Test {

    private val octopusses = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day11::part1 to 1656,
        Day11::part2 to 195
    )

    @Test
    fun testMySolution() {
        val day11 = Day11(octopusses)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day11) }
            println("Day11::${function.name}: ${octopusses.size * octopusses[0].length} octopusses-> " +
                    "$result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
