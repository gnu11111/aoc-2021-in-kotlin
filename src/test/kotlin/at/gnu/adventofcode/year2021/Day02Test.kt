package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day02Test {

    private val commands = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day02::part1 to 150,
        Day02::part2 to 900
    )

    @Test
    fun testMySolution() {
        val day02 = Day02(commands)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function.invoke(day02) }
            println("Day02::${function.name}: ${commands.size} commands -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
