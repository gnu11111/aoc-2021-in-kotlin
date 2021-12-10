package at.gnu.adventofcode.year2021

class Day10(private val navigationSubsystem: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day10.txt"
        val scoresPart1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        val scoresPart2 = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    }

    fun part1(): Long =
        navigationSubsystem.fold(0L) { acc, chunk -> acc + (scoresPart1[chunk.firstIllegalClosingBracket()] ?: 0) }

    fun part2(): Long {
        val score = mutableListOf<Long>()
        navigationSubsystem.asSequence().map { it.compress() }.filter { it.isIncomplete() }.forEach {
            score += it.calculateScore()
        }
        return score.sorted()[score.size / 2]
    }

    private fun String.isIncomplete() =
        this.firstOrNull { it in scoresPart1.keys } == null

    private fun String.calculateScore(): Long =
        this.foldRight(0L) { bracket, acc -> (5L * acc) + scoresPart2[bracket]!! }

    private fun String.firstIllegalClosingBracket() =
        this.compress().firstOrNull { it in scoresPart1.keys }

    private fun String.compress(): String {
        var line = this
        var lastLength = -1
        while (line.isNotEmpty() && (line.length != lastLength)) {
            lastLength = line.length
            line = line.replace("()", "").replace("[]", "").replace("{}", "").replace("<>", "")
        }
        return line
    }
}

fun main() {
    val navigationSubsystem = Day10::class.java.getResource(Day10.input)!!.readText().trim().split("\n", "\r\n")
    val day10 = Day10(navigationSubsystem)
    println("Day10::part1 = ${day10.part1()}")
    println("Day10::part2 = ${day10.part2()}")
}
