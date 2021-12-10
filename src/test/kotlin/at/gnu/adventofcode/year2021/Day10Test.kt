package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day10Test {

    private val navigationSubsystem = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day10::part1 to 26397L,
        Day10::part2 to 288957L
    )

    @Test
    fun testMySolution() {
        val day10 = Day10(navigationSubsystem)
        for (function in test.keys) {
            val result: Long
            val time = measureNanoTime { result = function(day10) }
            println("Day10::${function.name}: ${navigationSubsystem.size} lines -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
