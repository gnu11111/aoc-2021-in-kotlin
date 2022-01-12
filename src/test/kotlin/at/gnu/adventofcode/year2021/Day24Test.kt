package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day24Test {

    private val source = """
        inp w
        add z w
        mod z 2
        div w 2
        add y w
        mod y 2
        div w 2
        add x w
        mod x 2
        div w 2
        mod w 2
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day24::part1 to 89999999999999L,
        Day24::part2 to 21111111111111L
    )

    @Test
    fun testMySolution() {
        val day24 = Day24(source)
        for (function in test.keys) {
            val result: Long
            val time = measureNanoTime { result = function(day24) }
            println("Day24::${function.name}: ${source.size} instructions -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
