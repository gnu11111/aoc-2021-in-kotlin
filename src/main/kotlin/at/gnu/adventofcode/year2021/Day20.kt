package at.gnu.adventofcode.year2021

class Day20(private val imageEnhancingAlgorithm: String, private val scannerImage: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day20.txt"
        const val LIT_PIXEL = '#'
    }

    fun part1(): Int =
        (1..2).fold(scannerImage) { acc, it -> acc.enhanceImage(withDefaultColor(it)) }.countLitPixels()

    fun part2(): Int =
        (1..50).fold(scannerImage) { acc, it -> acc.enhanceImage(withDefaultColor(it)) }.countLitPixels()

    private fun List<String>.enhanceImage(defaultColor: Boolean = false): List<String> =
        (-1..this.size).map { y ->
            (-1..this.first().length).joinToString("") { x ->
                imageEnhancingAlgorithm[algorithmIndexFor(x, y, defaultColor)].toString()
            }
        }

    private fun withDefaultColor(iteration: Int): Boolean =
        when {
            imageEnhancingAlgorithm.first() == '.' -> false
            imageEnhancingAlgorithm.last() == '#' -> true
            else -> (iteration % 2) == 0
        }

    private fun List<String>.countLitPixels(): Int =
        this.sumOf { line -> line.count { it == LIT_PIXEL } }

    private fun List<String>.algorithmIndexFor(x: Int, y: Int, defaultColor: Boolean = false): Int =
        this.binaryIndexAt(x, y, defaultColor).binaryToInt()

    private fun String.binaryToInt(): Int =
        Integer.parseInt(this, 2)

    private fun List<String>.binaryIndexAt(x: Int, y: Int, defaultColor: Boolean = false) =
        this.binaryAt(x - 1, y - 1, defaultColor) + this.binaryAt(x, y - 1, defaultColor) +
        this.binaryAt(x + 1, y - 1, defaultColor) + this.binaryAt(x - 1, y, defaultColor) +
        this.binaryAt(x, y, defaultColor) + this.binaryAt(x + 1, y, defaultColor) +
        this.binaryAt(x - 1, y + 1, defaultColor) + this.binaryAt(x, y + 1, defaultColor) +
        this.binaryAt(x + 1, y + 1, defaultColor)

    private fun List<String>.binaryAt(x: Int, y: Int, defaultColor: Boolean = false): String =
        if (this.pixelAt(x, y, defaultColor)) "1" else "0"

    private fun List<String>.pixelAt(x: Int, y: Int, defaultColor: Boolean = false): Boolean =
        this.elementAtOrNull(y)?.elementAtOrNull(x)?.equals(LIT_PIXEL) ?: defaultColor
}

fun main() {
    val (imageEnhancingAlgorithm, scannerImage) = Day20::class.java.getResource(Day20.RESOURCE)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day20 = Day20(imageEnhancingAlgorithm.first(), scannerImage)
    println("Day20::part1 -> ${day20.part1()}")
    println("Day20::part2 -> ${day20.part2()}")
}
