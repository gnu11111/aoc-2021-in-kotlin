package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day06Test {

    private val input = listOf(3, 4, 3, 1, 2)

    private val test = mapOf(
        Day06::part1 to 5934L,
        Day06::part2 to 26984457539L
    )

    @Test
    fun testMySolution() {
        val day06 = Day06(input)
        for (function in test.keys) {
            val result: Long
            val time = measureNanoTime { result = function(day06) }
            println("Day06::${function.name}: $input -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
