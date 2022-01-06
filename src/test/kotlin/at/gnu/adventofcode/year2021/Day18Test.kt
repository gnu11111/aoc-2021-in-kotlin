package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day18Test {

    private val snailFishNumbers = """
        [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
        [[[5,[2,8]],4],[5,[[9,9],0]]]
        [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
        [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
        [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
        [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
        [[[[5,4],[7,7]],8],[[8,3],8]]
        [[9,3],[[9,9],[6,[4,9]]]]
        [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
        [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
    """.trimIndent().split("\n")

    private val test = mapOf(
        Day18::part1 to 4140,
        Day18::part2 to 3993
    )

    @Test
    fun testMySolution() {
        val day18 = Day18(snailFishNumbers)
        for (function in test.keys) {
            val result: Int
            val time = measureNanoTime { result = function(day18) }
            println("Day18::${function.name}: ${snailFishNumbers.size} snail-fish-numbers -> $result [${time}ns]")
            Assert.assertEquals(test[function], result)
        }
    }
}
