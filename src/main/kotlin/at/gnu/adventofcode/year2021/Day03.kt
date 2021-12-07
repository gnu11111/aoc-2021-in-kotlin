package at.gnu.adventofcode.year2021

import kotlin.math.pow

class Day03(private val diagnosticReport: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day03.txt"
    }

    enum class FilterType { MAJORITY, MINORITY }
    private val binaryDigits = diagnosticReport.first().length

    fun part1(): Int {
        val sumOfOnes = IntArray(binaryDigits) { position -> diagnosticReport.count { it[position] == '1' } }
        val gammaRate = sumOfOnes.convertToBinaryStringByMajorityOfOnes().convertBinaryToInt()
        val epsilonRate = gammaRate.binaryNegateInt()
        return (gammaRate * epsilonRate)
    }

    fun part2(): Int {
        val oxygenGeneratorRating = (0..binaryDigits).fold(diagnosticReport) { acc, position ->
            acc.filterLines(position, FilterType.MAJORITY)
        }
        val co2ScrubberRating = (0..binaryDigits).fold(diagnosticReport) { acc, position ->
            acc.filterLines(position, FilterType.MINORITY)
        }
        return (oxygenGeneratorRating.first().convertBinaryToInt() * co2ScrubberRating.first().convertBinaryToInt())
    }

    private fun List<String>.filterLines(position: Int, type: FilterType) =
        if (this.size > 1) {
            val sumOfOnes = this.count { it[position] == '1' }
            val digitToFilter = if (sumOfOnes >= ((this.size + 1) / 2)) '1' else '0'
            when (type) {
                FilterType.MAJORITY -> this.filter { it[position] == digitToFilter }
                FilterType.MINORITY -> this.filter { it[position] != digitToFilter }
            }
        } else
            this

    private fun IntArray.convertToBinaryStringByMajorityOfOnes() =
        this.map { if (it > (diagnosticReport.size / 2)) '1' else '0' }.joinToString("")

    private fun String.convertBinaryToInt() =
        Integer.parseInt(this, 2)

    private fun Int.binaryNegateInt() =
        2.0.pow(binaryDigits).toInt() - 1 - this
}

fun main() {
    val diagnosticReport = Day03::class.java.getResource(Day03.input)!!.readText().trim().split("\n", "\r\n")
    val day03 = Day03(diagnosticReport)
    println("Day03::part1 -> ${day03.part1()}")
    println("Day03::part2 -> ${day03.part2()}")
}
