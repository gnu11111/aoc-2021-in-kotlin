package at.gnu.adventofcode.year2021

class Day01(private val measurements: List<Int>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day01.txt"
    }

    fun part1(): Int =
        measurements.countIncreasingValues()

    fun part2(): Int =
        measurements.windowed(3, 1) { it.sum() }.countIncreasingValues()

    private fun List<Int>.countIncreasingValues() =
        this.zipWithNext().count { it.second > it.first }
}

fun main() {
    val input = Day01::class.java.getResource(Day01.RESOURCE)!!.readText().trim().split("\n", "\r\n").map { it.toInt() }
    val day01 = Day01(input)
    println("Day01::part1 -> ${day01.part1()}")
    println("Day01::part2 -> ${day01.part2()}")
}
