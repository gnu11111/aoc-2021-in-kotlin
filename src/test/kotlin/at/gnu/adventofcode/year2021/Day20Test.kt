package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day20Test {

    private val imageEnhancingAlgorithm =
        "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
        "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
        ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
        ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
        ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
        "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
        "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"

    private val scannerImage = """
        #..#.
        #....
        ##..#
        ..#..
        ..###
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day20::part1 to 35,
        Day20::part2 to 3351
    )

    @Test
    fun testMySolution() {
        val day20 = Day20(imageEnhancingAlgorithm, scannerImage)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day20) }
            println("Day20::${function.name}: ${scannerImage.size} x ${scannerImage.first().length} image -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
