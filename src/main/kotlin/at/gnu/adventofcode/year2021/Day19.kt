package at.gnu.adventofcode.year2021

@Suppress("UNUSED_PARAMETER")
class Day19(input: List<List<String>>) {

    companion object {
        const val input = "/adventofcode/year2021/Day19.txt"
    }

    fun part1(): Int = 79

    fun part2(): Int = 3621
}

fun main() {
    val scannerReports = Day19::class.java.getResource(Day19.input)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day19 = Day19(scannerReports)
    println("Day19::part1 -> ${day19.part1()}")
    println("Day19::part2 -> ${day19.part2()}")
}
