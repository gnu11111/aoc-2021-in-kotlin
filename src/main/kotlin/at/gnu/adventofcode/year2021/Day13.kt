package at.gnu.adventofcode.year2021

class Day13(dotsFromInput: List<String>, foldsFromInput: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day13.txt"
        private val outOfBounds = Dot(-1, -1)
    }

    data class Dot(val x: Int, val y: Int)
    enum class FoldMethod { HORIZONTAL, VERTICAL }
    sealed interface Fold
    class Horizontal(val y: Int) : Fold
    class Vertical(val x: Int) : Fold

    private val dots = dotsFromInput.map { it.split(",") }.map { Dot(it[0].toInt(), it[1].toInt()) }.toSet()
    private val folds = foldsFromInput.map {
        val (type, amount) = """fold along (.)=(\d+)""".toRegex().matchEntire(it)!!.destructured
        if (type == "x") Vertical(amount.toInt()) else Horizontal(amount.toInt())
    }

    fun part1(): Int =
        when (val fold = folds.first()) {
            is Horizontal -> dots.fold(fold.y, FoldMethod.HORIZONTAL)
            is Vertical -> dots.fold(fold.x, FoldMethod.VERTICAL)
        }.size

    fun part2(): Int {
        val newDots = folds.fold(dots) { acc, fold ->
            when (fold) {
                is Horizontal -> acc.fold(fold.y, FoldMethod.HORIZONTAL)
                is Vertical -> acc.fold(fold.x, FoldMethod.VERTICAL)
            }
        }
        newDots.prettyPrint()
        return newDots.size
    }

    private fun Set<Dot>.fold(border: Int, method: FoldMethod): Set<Dot> {
        val newDots = mutableSetOf<Dot>()
        this.map {
            when (method) {
                FoldMethod.HORIZONTAL -> if (it.y != border) newDots.add(it.foldHorizontal(border))
                FoldMethod.VERTICAL -> if (it.x != border) newDots.add(it.foldVertical(border))
            }
        }
        return newDots
    }

    private fun Dot.foldHorizontal(border: Int): Dot =
        if (this.y < border) this else if (this.y > border) Dot(this.x, (2 * border) - this.y) else outOfBounds

    private fun Dot.foldVertical(border: Int): Dot =
        if (this.x < border) this else if (this.x > border) Dot((2 * border) - this.x, this.y) else outOfBounds

    private fun Set<Dot>.prettyPrint() =
        (0..this.maxOf { it.y }).forEach { y ->
            (0..this.maxOf { it.x }).forEach { x -> print(if (this.contains(Dot(x, y))) "#" else ".") }
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
