package at.gnu.adventofcode.year2021

class Day11(val input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day11.txt"
        val outOfBounds = Octopus(-1, -1, 0)
    }

    data class Octopus(val x: Int, val y: Int, var energyLevel: Int = 0)

    private var octopusses = input.mapIndexed { y, line -> line.mapIndexed { x, char -> Octopus(x, y, char - '0') } }

    fun part1(): Int {
        var flashes = 0
        repeat(100) {
            octopusses.flatten().forEach { it.increaseEnergy(emptySet()) }
            octopusses.flatten().forEach { if (it.energyLevel > 9) { flashes++; it.energyLevel = 0 } }
        }
        return flashes
    }

    fun part2(): Int {
        var count = 0
        // TODO: octopus.energyLevels should not be mutable!
        octopusses = input.mapIndexed { y, line -> line.mapIndexed { x, char -> Octopus(x, y, char - '0') } }
        while (true) {
            count++
            var flashes = 0
            octopusses.flatten().forEach { it.increaseEnergy(emptySet()) }
            octopusses.flatten().forEach { if (it.energyLevel > 9) { flashes++; it.energyLevel = 0 } }
            if (flashes == octopusses.flatten().size)
                return count
        }
    }

    private fun Octopus.increaseEnergy(octopusses: Set<Octopus>): Set<Octopus> =
        when {
            (this === outOfBounds) || (this in octopusses) -> octopusses
            (this.energyLevel == 9) -> {
                this.energyLevel++
                this.neighbors().fold(octopusses + this) { acc, octopus -> octopus.increaseEnergy(acc) }
            }
            else -> { this.energyLevel++; octopusses }
        }

    private fun Octopus.neighbors(): List<Octopus> =
        listOf(octopusAt(x - 1, y), octopusAt(x + 1, y), octopusAt(x, y - 1), octopusAt(x, y + 1),
            octopusAt(x - 1, y + 1), octopusAt(x + 1, y + 1), octopusAt(x - 1, y - 1), octopusAt(x + 1, y - 1))

    private fun octopusAt(x: Int, y: Int): Octopus =
        octopusses.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
}

fun main() {
    val input = Day11::class.java.getResource(Day11.input)!!.readText().trim().split("\n", "\r\n")
    val day11 = Day11(input)
    println("Day11::part1 = ${day11.part1()}")
    println("Day11::part2 = ${day11.part2()}")
}
