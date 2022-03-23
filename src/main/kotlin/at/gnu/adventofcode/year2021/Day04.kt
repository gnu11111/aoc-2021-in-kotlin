package at.gnu.adventofcode.year2021

class Day04(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day04.txt"
        const val boardSize = 5
    }

    class Board(val winningNumbers: Set<Set<Int>>)

    private val drawnNumbers: List<Int> = input.first().split(",").map { it.toInt() }
    private val boards: List<Board>

    init {
        val newBoards = mutableListOf<Board>()
        for (i in 0 until ((input.size - 1) / (boardSize + 1))) {
            val winningNumbers = mutableSetOf<Set<Int>>()
            val colNumbers = MutableList(boardSize) { mutableSetOf<Int>() }
            for (j in 0 until boardSize) {
                val line = (i * (boardSize + 1)) + j + 2
                val rowNumbers = input[line].trim().split("""\s+""".toRegex()).map { it.toInt() }
                rowNumbers.forEachIndexed { row, number -> colNumbers[row].add(number) }
                winningNumbers.add(rowNumbers.toSet())
            }
            winningNumbers.addAll(colNumbers)
            val board = Board(winningNumbers.toSet())
            newBoards.add(board)
        }
        boards = newBoards.toList()
    }

    fun part1(): Int =
        calculateWinningBoard(1)

    fun part2(): Int =
        calculateWinningBoard(boards.size)

    private fun calculateWinningBoard(i: Int): Int {
        val winningBoards = mutableSetOf<Board>()
        val winningNumbers = mutableSetOf<Int>()
        for (drawnNumber in drawnNumbers) {
            winningNumbers.add(drawnNumber)
            for (board in boards) {
                if (board in winningBoards)
                    continue
                for (numbers in board.winningNumbers) {
                    if (winningNumbers.containsAll(numbers)) {
                        winningBoards.add(board)
                        if (winningBoards.size == i) {
                            val remainingNumbers = (board.winningNumbers.flatten().toSet() - winningNumbers).toSet()
                            return remainingNumbers.sum() * winningNumbers.last()
                        }
                        break
                    }
                }
            }
        }
        return -1
    }
}

fun main() {
    val input = Day04::class.java.getResource(Day04.input)!!.readText().trim().split("\n", "\r\n")
    val day04 = Day04(input)
    println("Day04::part1 -> ${day04.part1()}")
    println("Day04::part2 -> ${day04.part2()}")
}
