package at.gnu.adventofcode.year2021

class Day08(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day08.txt"
    }

    data class Display(val signalPatterns: List<Set<Char>>, val outputValues: List<Set<Char>>)

    private val displays: List<Display> = input.fold(listOf()) { acc, line ->
        val (signalPatternsStrings, outputValuesString) = line.split(" | ").map { it.split(" ") }
        val signalPatterns = signalPatternsStrings.map { it.asSequence().sorted().toSet() }
        val outputValues = outputValuesString.map { it.asSequence().sorted().toSet() }
        acc + Display(signalPatterns, outputValues)
    }

    fun part1(): Int =
        displays.fold(0) { acc, display -> acc + display.countAmountOfNumbersOneFourSevenEight() }

    fun part2(): Int {
        var sum = 0
        for (display in displays) {
            val map = mutableMapOf<Int, Set<Char>>()
            val sizeFive = display.getPatternOfSize(5)
            val sizeSix = display.getPatternOfSize(6)

            map[1] = display.getPatternOfSize(2).first()
            map[4] = display.getPatternOfSize(4).first()
            map[7] = display.getPatternOfSize(3).first()
            map[8] = display.getPatternOfSize(7).first()
            map[9] = sizeSix.first { it.containsAll(map[1]!! union map[4]!!) }

            val zeroSix = sizeSix.filter { !it.containsAll(map[9]!!) }
            val e = map[8]!! - map[9]!!
            val possibleSix = sizeFive.map { it + e }

            map[6] = possibleSix.first { (it == zeroSix[0]) || (it == zeroSix[1]) }
            map[5] = map[6]!! - e
            map[0] = zeroSix.first { it != map[6] }
            map[2] = sizeFive.first { it.containsAll(e) }
            map[3] = sizeFive.first { (it != map[2]) && (it != map[5]) }

            var number = 0
            for (value in display.outputValues)
                number = (10 * number) + map.entries.first { it.value == value }.key
            sum += number
        }
        return sum
    }

    private fun Display.getPatternOfSize(size: Int): List<Set<Char>> =
        this.signalPatterns.filter { it.size == size }

    private fun Display.countAmountOfNumbersOneFourSevenEight(): Int =
        this.outputValues.map { it.size }.count { (it == 2) || (it == 3) || (it == 4) || (it == 7) }
}

fun main() {
    val input = Day08::class.java.getResource(Day08.input)!!.readText().trim().split("\n", "\r\n")
    val day08 = Day08(input)
    println("Day08::part1 -> ${day08.part1()}")
    println("Day08::part2 -> ${day08.part2()}")
}
