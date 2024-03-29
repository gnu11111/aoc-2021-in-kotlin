package at.gnu.adventofcode.year2021

class Day08(input: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day08.txt"
    }

    class Display(val signalPatterns: List<Set<Char>>, val outputValues: List<Set<Char>>)

    private val displays: List<Display> = input.fold(listOf()) { acc, line ->
        val (signalPatternsStrings, outputValuesStrings) = line.split(" | ").map { it.split(" ") }
        val signalPatterns = signalPatternsStrings.map { it.asSequence().sorted().toSet() }
        val outputValues = outputValuesStrings.map { it.asSequence().sorted().toSet() }
        acc + Display(signalPatterns, outputValues)
    }

    fun part1(): Int =
        displays.fold(0) { acc, display -> acc + display.outputValues.count { it.size in setOf(2, 3, 4, 7) } }

    fun part2(): Int {
        var sum = 0
        for (display in displays) {
            val map = mutableMapOf<Int, Set<Char>>()
            val twoThreeFive = display.getPatternsOfSize(5)
            val zeroSixNine = display.getPatternsOfSize(6)

            map[1] = display.getPatternsOfSize(2).first()
            map[4] = display.getPatternsOfSize(4).first()
            map[7] = display.getPatternsOfSize(3).first()
            map[8] = display.getPatternsOfSize(7).first()
            map[9] = zeroSixNine.first { it.containsAll(map[4]!!) }

            val zeroSix = zeroSixNine.filter { it != map[9]!! }
            val e = map[8]!! - map[9]!!
            val possibleSix = twoThreeFive.map { it + e }

            map[6] = possibleSix.first { (it == zeroSix[0]) || (it == zeroSix[1]) }
            map[5] = map[6]!! - e
            map[0] = zeroSix.first { it != map[6] }
            map[2] = twoThreeFive.first { it.containsAll(e) }
            map[3] = twoThreeFive.first { (it != map[2]) && (it != map[5]) }

            var number = 0
            for (value in display.outputValues)
                number = (10 * number) + map.entries.first { it.value == value }.key
            sum += number
        }
        return sum
    }

    private fun Display.getPatternsOfSize(size: Int): List<Set<Char>> =
        this.signalPatterns.filter { it.size == size }
}

fun main() {
    val input = Day08::class.java.getResource(Day08.RESOURCE)!!.readText().trim().split("\n", "\r\n")
    val day08 = Day08(input)
    println("Day08::part1 -> ${day08.part1()}")
    println("Day08::part2 -> ${day08.part2()}")
}
