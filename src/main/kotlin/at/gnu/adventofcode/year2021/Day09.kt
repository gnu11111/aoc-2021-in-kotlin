package at.gnu.adventofcode.year2021

class Day09(val input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day09.txt"
        const val threshold = 9
        val outOfBounds = Point(0, 0, 10)
    }

    data class Point(val x: Int, val y: Int, val value: Int)

    private val heightmap = input.mapIndexed { y, line -> line.mapIndexed { x, char -> Point(x, y, char - '0') } }

    fun part1(): Int =
        heightmap.getLowPoints().sumOf { it.value + 1 }

    fun part2(): Int =
        heightmap.getLowPoints().map { it.fill(emptySet()).size }.sortedDescending().take(3).reduce(Int::times)

    private fun Point.fill(points: Set<Point>): Set<Point> =
        when {
            (this.value >= threshold) || (this in points) -> points
            else -> this.adjacentPoints().fold(points + this) { acc, point -> point.fill(acc) }
        }

    private fun List<List<Point>>.getLowPoints(): List<Point> =
        this.flatten().filter { it.isLowPoint() }

    private fun Point.isLowPoint(): Boolean =
        this.adjacentPoints().all { value < it.value }

    private fun Point.adjacentPoints(): List<Point> =
        listOf(getPointAt(x - 1, y), getPointAt(x + 1, y), getPointAt(x, y - 1), getPointAt(x, y + 1))

    private fun getPointAt(x: Int, y: Int): Point =
        heightmap.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
}

fun main() {
    val input = Day09::class.java.getResource(Day09.input)!!.readText().trim().split("\n", "\r\n")
    val day09 = Day09(input)
    println("Day09::part1 -> ${day09.part1()}")
    println("Day09::part2 -> ${day09.part2()}")
}
