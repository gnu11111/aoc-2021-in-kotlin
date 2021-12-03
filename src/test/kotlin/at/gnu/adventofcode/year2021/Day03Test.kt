package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day03Test {

    private val diagnosticReport = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day03::part1 to 198,
        Day03::part2 to 230
    )

    @Test
    fun testMySolution() {
        val day03 = Day03(diagnosticReport)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day03) }
            println("Day03::${function.name}: ${diagnosticReport.size} lines -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
