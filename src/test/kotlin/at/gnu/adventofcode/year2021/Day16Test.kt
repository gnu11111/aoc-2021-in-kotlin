package at.gnu.adventofcode.year2021

import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class Day16Test {

    private val test = mapOf(
        (Day16::part1 to 1) to ("8A004A801A8002F478" to 16L),
        (Day16::part1 to 2) to ("620080001611562C8802118E34" to 12L),
        (Day16::part1 to 3) to ("C0015000016115A2E0802F182340" to 23L),
        (Day16::part1 to 4) to ("A0016C880162017C3686B18A3D4780" to 31L),
        (Day16::part2 to 1) to ("C200B40A82" to 3L),
        (Day16::part2 to 2) to ("04005AC33890" to 54L),
        (Day16::part2 to 3) to ("880086C3E88112" to 7L),
        (Day16::part2 to 4) to ("CE00C43D881120" to 9L),
        (Day16::part2 to 5) to ("D8005AC2A8F0" to 1L),
        (Day16::part2 to 6) to ("F600BC2D8F" to 0L),
        (Day16::part2 to 7) to ("9C005AC2F8F0" to 0L),
        (Day16::part2 to 8) to ("9C0141080250320F1802104A08" to 1L)
    )

    @Test
    fun testMySolution() {
        for (function in test.keys) {
            val data = test[function]!!
            val result: Long
            val time = measureNanoTime { result = function.first.invoke(Day16(data.first)) }
            println("Day01::${function.first.name}: ${data.first} -> $result [${time}ns]")
            Assert.assertEquals(data.second, result)
        }
    }
}
