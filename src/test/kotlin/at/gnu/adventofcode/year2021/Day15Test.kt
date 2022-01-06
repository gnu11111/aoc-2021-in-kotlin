package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day15Test {

    private val risk = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day15::part1 to 40,
        Day15::part2 to 315
    )

    @Test
    fun testMySolution() {
        val day15 = Day15(risk)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day15) }
            println("Day15::${function.name}: ${risk.size} lines -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
