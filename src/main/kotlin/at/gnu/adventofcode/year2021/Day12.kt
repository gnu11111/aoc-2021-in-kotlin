package at.gnu.adventofcode.year2021

class Day12(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day12.txt"
        const val start = "start"
        const val end = "end"
    }

    data class Path(val from: String, val to: String)

    private val paths = input.map { it.split("-") }.flatMap {
        Pair(Path(it[0], it[1]), Path(it[1], it[0])).toList()
    }.toSet()

    fun part1(): Int =
        paths.getPathsTo(start).sumOf { it.sumOfPaths() }

    fun part2(): Int =
        paths.getPathsTo(start).sumOf { it.sumOfPaths(twice = true) }

    private fun Path.sumOfPaths(visitedCaves: Set<String> = emptySet(), twice: Boolean = false): Int {
        val isFirstVisit = twice && (this.to != start) && this.to.all(Char::isLowerCase) && (this.to in visitedCaves)
        val isEndPoint = (this.to == end)
        return when {
            isEndPoint -> 1
            isFirstVisit -> paths.getPathsTo(this.to).sumOf { it.sumOfPaths(visitedCaves + this.from, false) }
            else -> paths.getPathsTo(this.to, visitedCaves).sumOf { it.sumOfPaths(visitedCaves + this.from, twice) }
        }
    }

    private fun Set<Path>.getPathsTo(cave: String, visitedCaves: Set<String> = emptySet()): Set<Path> =
        this.filter { (it.from == cave) && !(cave.all(Char::isLowerCase) && visitedCaves.contains(cave)) }.toSet()
}

fun main() {
    val input = Day12::class.java.getResource(Day12.input)!!.readText().trim().split("\n", "\r\n")
    val day12 = Day12(input)
    println("Day12::part1 -> ${day12.part1()}")
    println("Day12::part2 -> ${day12.part2()}")
}
