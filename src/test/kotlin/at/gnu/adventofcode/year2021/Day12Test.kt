package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day12Test {

    private val paths = listOf(
        listOf("start-A", "start-b", "A-c", "A-b", "b-d", "A-end", "b-end"),
        listOf("dc-end", "HN-start", "start-kj", "dc-start", "dc-HN", "LN-dc", "HN-end", "kj-sa", "kj-HN", "kj-dc"),
        listOf("fs-end", "he-DX", "fs-he", "start-DX", "pj-DX", "end-zg", "zg-sl", "zg-pj", "pj-he", "RW-he", "fs-DX",
            "pj-RW", "zg-RW", "start-pj", "he-WI", "zg-he", "pj-fs", "start-RW")
    )

    private val test = mapOf(
        (Day12::part1 to 1) to (paths[0] to 10),
        (Day12::part1 to 2) to (paths[1] to 19),
        (Day12::part1 to 3) to (paths[2] to 226),
        (Day12::part2 to 1) to (paths[0] to 36),
        (Day12::part2 to 2) to (paths[1] to 103),
        (Day12::part2 to 3) to (paths[2] to 3509)
    )

    @Test
    fun testMySolution() {
        for (function in test.keys) {
            val data = test[function]!!
            val result: Int
            val time = measureNanoTime { result = function.first.invoke(Day12(data.first)) }
            println("Day12::${function.first.name}: ${data.first.size} paths -> $result [${time}ns]")
            Assert.assertEquals(data.second, result)
        }
    }
}
