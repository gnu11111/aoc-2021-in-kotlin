package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day14Test {

    private val polymerTemplate = "NNCB"
    private val pairInsertionRules = listOf("CH -> B", "HH -> N", "CB -> H", "NH -> C", "HB -> C", "HC -> B",
        "HN -> C", "NN -> C", "BH -> H", "NC -> B", "NB -> B", "BN -> B", "BB -> N", "BC -> B", "CC -> N", "CN -> C")

    private val test = mapOf(
        Day14::part1 to 1588L,
        Day14::part2 to 2188189693529L
    )

    @Test
    fun testMySolution() {
        val day14 = Day14(polymerTemplate, pairInsertionRules)
        for (function in test.keys) {
            val result: Long
            val time = measureNanoTime { result = function(day14) }
            println("Day14::${function.name}: $polymerTemplate,${pairInsertionRules.size} rules -> $result [${time}ms]")
            Assert.assertEquals(test[function], result)
        }
    }
}
