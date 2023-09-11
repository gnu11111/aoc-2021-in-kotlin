package at.gnu.adventofcode.year2021

class Day12(input: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day12.txt"
        const val START = "start"
        const val END = "end"
    }

    class Path(val from: String, val to: String)

    private val paths = input.map { it.split("-") }
        .flatMap { Pair(Path(it[0], it[1]), Path(it[1], it[0])).toList() }.toSet()

    fun part1(): Int =
        paths.getPathsFrom(START).sumOf { it.countPaths() }

    fun part2(): Int =
        paths.getPathsFrom(START).sumOf { it.countPaths(twice = true) }

    private fun Path.countPaths(visitedCaves: Set<String> = emptySet(), twice: Boolean = false): Int {
        val isEndPoint = (this.to == END)
        val isSecondVisit = twice && (this.to != START) && this.to.isSmallCaveVisited(visitedCaves)
        return when {
            isEndPoint -> 1
            isSecondVisit -> paths.getPathsFrom(this.to).sumOf { it.countPaths(visitedCaves + this.from, false) }
            else -> paths.getPathsFrom(this.to, visitedCaves).sumOf { it.countPaths(visitedCaves + this.from, twice) }
        }
    }

    private fun Set<Path>.getPathsFrom(cave: String, visitedCaves: Set<String> = emptySet()): Set<Path> =
        this.filter { (it.from == cave) && !cave.isSmallCaveVisited(visitedCaves) }.toSet()

    private fun String.isSmallCaveVisited(visitedCaves: Set<String>): Boolean =
        this.all(Char::isLowerCase) && (this in visitedCaves)
}

fun main() {
    val input = Day12::class.java.getResource(Day12.RESOURCE)!!.readText().trim().split("\n", "\r\n")
    val day12 = Day12(input)
    println("Day12::part1 -> ${day12.part1()}")
    println("Day12::part2 -> ${day12.part2()}")
}
