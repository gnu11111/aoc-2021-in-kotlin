package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day21Test {

    private val startingPositions = listOf("Player 1 starting position: 4", "Player 2 starting position: 8")

    private val test = mapOf(
        Day21::part1 to 739785L,
        Day21::part2 to 444356092776315L
    )

    @Test
    fun testMySolution() {
        val day21 = Day21(startingPositions)
        for (function in test.keys) {
            val result: Long
            val time = measureNanoTime { result = function(day21) }
            println("Day21::${function.name}: $startingPositions -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
