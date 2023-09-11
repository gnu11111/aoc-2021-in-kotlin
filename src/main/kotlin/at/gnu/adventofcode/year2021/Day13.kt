package at.gnu.adventofcode.year2021

class Day13(dotsFromInput: List<String>, foldingsFromInput: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day13.txt"
        const val VERTICAL_FOLDER_TYPE = "x"
        private val foldingCommand = """fold along ([xy])=(\d+)""".toRegex()
        private val outOfBounds = Dot(-1, -1)
    }

    data class Dot(val x: Int, val y: Int)
    sealed class Folding(val transform: (Dot) -> Dot)

    private val dots = dotsFromInput.map { it.split(",") }.map { Dot(it[0].toInt(), it[1].toInt()) }.toSet()
    private val foldings = foldingsFromInput.map {
        val (type, amount) = foldingCommand.matchEntire(it)!!.destructured
        if (type == VERTICAL_FOLDER_TYPE) VerticalFolding(amount.toInt()) else HorizontalFolding(amount.toInt())
    }

    fun part1(): Int =
        dots.creasePaperUsing(foldings.first()).size

    fun part2(): Int =
        foldings.fold(dots) { newDots, folding -> newDots.creasePaperUsing(folding) }.also { prettyPrint(it) }.size

    private fun Set<Dot>.creasePaperUsing(folding: Folding): Set<Dot> =
        this.map { dot -> folding.transform(dot) }.toSet()

    class HorizontalFolding(y: Int) : Folding({ dot ->
        if (dot.y < y) dot else if (dot.y > y) Dot(dot.x, (2 * y) - dot.y) else outOfBounds
    })

    class VerticalFolding(x: Int) : Folding({ dot ->
        if (dot.x < x) dot else if (dot.x > x) Dot((2 * x) - dot.x, dot.y) else outOfBounds
    })

    private fun prettyPrint(dots: Set<Dot>) =
        (0..dots.maxOf { it.y }).forEach { y ->
            (0..dots.maxOf { it.x }).forEach { x -> print(if (dots.contains(Dot(x, y))) "#" else ".") }
            println("")
        }
}

fun main() {
    val (dots, foldings) = Day13::class.java.getResource(Day13.RESOURCE)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day13 = Day13(dots, foldings)
    println("Day13::part1 -> ${day13.part1()}")
    println("Day13::part2 -> ${day13.part2()}")
}
