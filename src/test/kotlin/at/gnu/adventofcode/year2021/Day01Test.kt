package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day01Test {

    private val measurements = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    private val test = mapOf(
        Day01::part1 to (measurements to 7),
        Day01::part2 to (measurements to 5)
    )

    @Test
    fun testMySolution() {
        for (function in test.keys) {
            val data = test[function]!!
            val result: Int
            val time = measureNanoTime { result = function.invoke(Day01(data.first)) }
            println("Day01::${function.name}: ${data.first} -> $result [${time}ns]")
            Assert.assertEquals(data.second, result)
        }
    }
}
