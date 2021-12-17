package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day17Test {

    private val targetArea = "target area: x=20..30, y=-10..-5"

    private val test = mapOf(
        Day17::part1 to 45,
        Day17::part2 to 112
    )

    @Test
    fun testMySolution() {
        val day17 = Day17(targetArea)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day17) }
            println("Day17::${function.name}: $targetArea -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
