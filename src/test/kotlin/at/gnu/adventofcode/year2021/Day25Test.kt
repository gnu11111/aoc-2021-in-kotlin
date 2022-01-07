package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day25Test {

    private val cucumbersMap = """
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day25::part1 to 58
    )

    @Test
    fun testMySolution() {
        val day25 = Day25(cucumbersMap)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day25) }
            println("Day25::${function.name}: ${cucumbersMap.size} rows -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
