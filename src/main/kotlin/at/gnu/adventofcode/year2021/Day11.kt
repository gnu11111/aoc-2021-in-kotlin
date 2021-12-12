package at.gnu.adventofcode.year2021

class Day11(val input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day11.txt"
        val outOfBounds = Octopus(-1, -1, 0)
    }

    data class Octopus(val x: Int, val y: Int, var energyLevel: Int = 0)

    private val maxX = input.first().length
    private val maxY = input.size
    private var octopusses: List<Octopus> = emptyList()

    fun part1(): Int {
        octopusses = createOctopusses()
        var flashes = 0
        repeat(100) {
            octopusses.forEach { it.increaseEnergy(emptySet()) }
            octopusses.forEach { if (it.energyLevel > 9) { flashes++; it.energyLevel = 0 } }
        }
        return flashes
    }

    fun part2(): Int {
        octopusses = createOctopusses()
        var count = 0
        while (true) {
            count++
            octopusses.forEach { it.increaseEnergy(emptySet()) }
            val flashes = octopusses.filter { it.energyLevel > 9 }.map { it.energyLevel = 0 }.count()
            if (flashes == octopusses.size)
                return count
        }
    }

    private fun createOctopusses() =
        input.mapIndexed { y, line -> line.mapIndexed { x, char -> Octopus(x, y, char - '0') } }.flatten()

    private fun Octopus.increaseEnergy(octopusses: Set<Octopus>): Set<Octopus> =
        when {
            (this === outOfBounds) || (this in octopusses) -> octopusses
            (this.energyLevel == 9) -> this.affectNeighbors(octopusses)
            else -> { this.energyLevel++; octopusses }
        }

    private fun Octopus.affectNeighbors(octopusses: Set<Octopus>): Set<Octopus> {
        this.energyLevel++
        return this.neighbors().fold(octopusses + this) { acc, octopus -> octopus.increaseEnergy(acc) }
    }

    private fun Octopus.neighbors(): List<Octopus> =
        listOf(octopusAt(x - 1, y), octopusAt(x + 1, y), octopusAt(x, y - 1), octopusAt(x, y + 1),
            octopusAt(x - 1, y + 1), octopusAt(x + 1, y + 1), octopusAt(x - 1, y - 1), octopusAt(x + 1, y - 1))

    private fun octopusAt(x: Int, y: Int): Octopus =
        if ((x >= 0) && (x < maxX) && (y >= 0) && (y < maxY))
            octopusses.elementAt((y * maxX) + x)
        else
            outOfBounds
}

fun main() {
    val input = Day11::class.java.getResource(Day11.input)!!.readText().trim().split("\n", "\r\n")
    val day11 = Day11(input)
    println("Day11::part1 = ${day11.part1()}")
    println("Day11::part2 = ${day11.part2()}")
}
