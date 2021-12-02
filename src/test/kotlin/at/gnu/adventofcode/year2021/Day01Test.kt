package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day01Test {

    private val measurements = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    private val test = mapOf(
        Day01::part1 to 7,
        Day01::part2 to 5
    )

    @Test
    fun testMySolution() {
        val day01 = Day01(measurements)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day01) }
            println("Day01::${function.name}: $measurements -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
