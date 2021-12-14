package at.gnu.adventofcode.year2021

class Day13(dotsFromInput: List<String>, foldersFromInput: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day13.txt"
        const val verticalFolderType = "x"
        private val folderCommand = """fold along ([xy])=(\d+)""".toRegex()
        private val outOfBounds = Dot(-1, -1)
    }

    data class Dot(val x: Int, val y: Int)
    sealed class Folder(val transform: (Dot) -> Dot)

    private val dots = dotsFromInput.map { it.split(",") }.map { Dot(it[0].toInt(), it[1].toInt()) }.toSet()
    private val folders = foldersFromInput.map {
        val (type, amount) = folderCommand.matchEntire(it)!!.destructured
        if (type == verticalFolderType) VerticalFolder(amount.toInt()) else HorizontalFolder(amount.toInt())
    }

    fun part1(): Int =
        dots.creasePaperUsing(folders.first()).size

    fun part2(): Int =
        folders.fold(dots) { newDots, folder -> newDots.creasePaperUsing(folder) }.also { prettyPrint(it) }.size

    private fun Set<Dot>.creasePaperUsing(folder: Folder): Set<Dot> =
        this.map { dot -> folder.transform(dot) }.toSet()

    class HorizontalFolder(y: Int) : Folder({ dot ->
        if (dot.y < y) dot else if (dot.y > y) Dot(dot.x, (2 * y) - dot.y) else outOfBounds
    })

    class VerticalFolder(x: Int) : Folder({ dot ->
        if (dot.x < x) dot else if (dot.x > x) Dot((2 * x) - dot.x, dot.y) else outOfBounds
    })

    private fun prettyPrint(dots: Set<Dot>) =
        (0..dots.maxOf { it.y }).forEach { y ->
            (0..dots.maxOf { it.x }).forEach { x -> print(if (dots.contains(Dot(x, y))) "#" else ".") }
            println("")
        }
}

fun main() {
    val (dots, folders) = Day13::class.java.getResource(Day13.input)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day13 = Day13(dots, folders)
    println("Day13::part1 -> ${day13.part1()}")
    println("Day13::part2 -> ${day13.part2()}")
}
