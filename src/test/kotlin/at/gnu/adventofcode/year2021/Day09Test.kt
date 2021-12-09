package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day09Test {

    private val heightmap = listOf("2199943210", "3987894921", "9856789892", "8767896789", "9899965678")

    private val test = mapOf(
        Day09::part1 to 15,
        Day09::part2 to 1134
    )

    @Test
    fun testMySolution() {
        val day09 = Day09(heightmap)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day09) }
            println("Day09::${function.name}: $heightmap -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
