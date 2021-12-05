package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day05Test {

    private val lines = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
        """.trimIndent().split("\n")

    private val test = mapOf(
        Day05::part1 to 5,
        Day05::part2 to 12
    )

    @Test
    fun testMySolution() {
        val day05 = Day05(lines)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day05) }
            println("Day05::${function.name}: ${lines.size} lines -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
