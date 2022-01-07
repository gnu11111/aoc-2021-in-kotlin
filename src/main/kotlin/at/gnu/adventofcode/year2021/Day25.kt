package at.gnu.adventofcode.year2021

class Day25(private val cucumbersMap: List<String>) {


    companion object {
        const val input = "/adventofcode/year2021/Day25.txt"
    }

    private val maxX = cucumbersMap.first().length
    private val maxY = cucumbersMap.size

    fun part1(): Int {
        var map = listOf(StringBuilder())
        var downMap = cucumbersMap.map { StringBuilder(it) }
        var count = 0
        while (map.isNotEqualTo(downMap)) {
            count++
            map = downMap
            val rightMap = createEmptyMap()
            for (y in 0 until maxY)
                for (x in 0 until maxX)
                    if (map[y][x] == '>')
                        rightMap.moveRight(x, y, map)
                    else if (map[y][x] == 'v')
                        rightMap[y][x] = 'v'
            downMap = createEmptyMap()
            for (y in 0 until maxY)
                for (x in 0 until maxX)
                    if (rightMap[y][x] == 'v')
                        downMap.moveDown(x, y, rightMap)
                    else if (rightMap[y][x] == '>')
                        downMap[y][x] = '>'
        }
        return count
    }

    private fun createEmptyMap() =
        MutableList(maxY) { StringBuilder(".".repeat(maxX)) }

    private fun MutableList<StringBuilder>.moveRight(x: Int, y: Int, oldMap: List<StringBuilder>) =
        if (oldMap[y][(x + 1) % maxX] == '.') this[y][(x + 1) % maxX] = '>' else this[y][x] = '>'

    private fun MutableList<StringBuilder>.moveDown(x: Int, y: Int, oldMap: List<StringBuilder>) =
        if (oldMap[(y + 1) % maxY][x] == '.') this[(y + 1) % maxY][x] = 'v' else this[y][x] = 'v'

    private fun List<StringBuilder>.isNotEqualTo(otherMap: List<StringBuilder>): Boolean =
        this.indices.any { this[it].toString() != otherMap[it].toString() }
}

fun main() {
    val input = Day25::class.java.getResource(Day25.input)!!.readText().trim().split("\n", "\r\n")
    val day25 = Day25(input)
    println("Day25::part1 -> ${day25.part1()}")
}
