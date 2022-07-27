package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day23Test {

    private val amphipods = """
        #############
        #...........#
        ###B#C#B#D###
          #A#D#C#A#
          #########
    """.trimIndent().split("\n")

    private val amphipods2 = """
        #############
        #...........#
        ###B#C#B#D###
          #D#C#B#A#
          #D#B#A#C#
          #A#D#C#A#
          #########
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day23::part1 to (amphipods to 14627),
        Day23::part2 to (amphipods2 to 41591)
    )

    @Test
    fun testMySolution() {
        for (function in test.keys) {
            val data = test[function]!!
            val result: Int
            val time = measureNanoTime { result = function(Day23(data.first)) }
            println("Day23::${function.name}: ${data.first.size} lines -> $result [${time}ns]")
            Assert.assertEquals(data.second, result)
        }
    }
}
