package at.gnu.adventofcode.year2021

class Day13(dotsFromInput: List<String>, foldsFromInput: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day13.txt"
        const val foldCommandVerticalType = "x"
        private val foldCommand = """fold along ([xy])=(\d+)""".toRegex()
        private val outOfBounds = Dot(-1, -1)
    }

    data class Dot(val x: Int, val y: Int)
    sealed class Fold(val transform: (Dot) -> Dot)

    private val dots = dotsFromInput.map { it.split(",") }.map { Dot(it[0].toInt(), it[1].toInt()) }.toSet()
    private val folds = foldsFromInput.map {
        val (type, amount) = foldCommand.matchEntire(it)!!.destructured
        if (type == foldCommandVerticalType) FoldVertical(amount.toInt()) else FoldHorizontal(amount.toInt())
    }

    fun part1(): Int =
        dots.creasePaper(folds.first()).size

    fun part2(): Int =
        folds.fold(dots) { acc, fold -> acc.creasePaper(fold) }.also { prettyPrint(it) }.size

    private fun Set<Dot>.creasePaper(fold: Fold): Set<Dot> =
        this.map { fold.transform(it) }.toSet()

    class FoldHorizontal(y: Int) : Fold({ dot ->
        if (dot.y < y) dot else if (dot.y > y) Dot(dot.x, (2 * y) - dot.y) else outOfBounds
    })

    class FoldVertical(x: Int) : Fold({ dot ->
        if (dot.x < x) dot else if (dot.x > x) Dot((2 * x) - dot.x, dot.y) else outOfBounds
    })

    private fun prettyPrint(dots: Set<Dot>) =
        (0..dots.maxOf { it.y }).forEach { y ->
            (0..dots.maxOf { it.x }).forEach { x -> print(if (dots.contains(Dot(x, y))) "#" else ".") }
            println("")
        }
}

fun main() {
    val (dots, folds) = Day13::class.java.getResource(Day13.input)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day13 = Day13(dots, folds)
    println("Day13::part1 -> ${day13.part1()}")
    println("Day13::part2 -> ${day13.part2()}")
}
