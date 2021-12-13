package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day13Test {

    private val input = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0
        
        fold along y=7
        fold along x=5
        """.trimIndent().split("\n\n").map { it.split("\n") }


    private val test = mapOf(
        Day13::part1 to 17,
        Day13::part2 to 16
    )

    @Test
    fun testMySolution() {
        val day13 = Day13(input[0], input[1])
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day13) }
            println("Day13::${function.name}: ${input[0].size} dots, ${input[1].size} folds  -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
