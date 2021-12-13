package at.gnu.adventofcode.year2021

class Day13(dotsFromInput: List<String>, foldsFromInput: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day13.txt"
        private val outOfBounds = Dot(-1, -1)
    }

    data class Dot(val x: Int, val y: Int)
    sealed class Fold(val transformation: (Dot) -> Dot)

    private val dots = dotsFromInput.map { it.split(",") }.map { Dot(it[0].toInt(), it[1].toInt()) }.toSet()
    private val folds = foldsFromInput.map {
        val (type, amount) = """fold along (.)=(\d+)""".toRegex().matchEntire(it)!!.destructured
        if (type == "x") FoldVertical(amount.toInt()) else FoldHorizontal(amount.toInt())
    }

    fun part1(): Int =
        dots.process(folds.first()).size

    fun part2(): Int =
        folds.fold(dots) { acc, fold -> acc.process(fold) }.prettyPrint()

    private fun Set<Dot>.process(fold: Fold): Set<Dot> =
        this.map { fold.transformation(it) }.toSet()

    private fun Set<Dot>.prettyPrint(): Int =
        (0..this.maxOf { it.y }).forEach { y ->
            (0..this.maxOf { it.x }).forEach { x -> print(if (this.contains(Dot(x, y))) "#" else ".") }
            println("")
        }.hashCode()

    class FoldHorizontal(y: Int) : Fold({ dot ->
        if (dot.y < y) dot else if (dot.y > y) Dot(dot.x, (2 * y) - dot.y) else outOfBounds
    })

    class FoldVertical(x: Int) : Fold({ dot ->
        if (dot.x < x) dot else if (dot.x > x) Dot((2 * x) - dot.x, dot.y) else outOfBounds
    })
}

fun main() {
    val (dots, folds) = Day13::class.java.getResource(Day13.input)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day13 = Day13(dots, folds)
    println("Day13::part1 -> ${day13.part1()}")
    println("Day13::part2 -> ${day13.part2()}")
}
