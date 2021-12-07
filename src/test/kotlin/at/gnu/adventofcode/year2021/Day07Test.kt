package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day07Test {

    private val crabPositions = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    private val test = mapOf(
        Day07::part1 to 37,
        Day07::part2 to 168
    )

    @Test
    fun testMySolution() {
        val day07 = Day07(crabPositions)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day07) }
            println("Day07::${function.name}: $crabPositions -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
