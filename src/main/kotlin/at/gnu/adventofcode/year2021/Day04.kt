package at.gnu.adventofcode.year2021

class Day04(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day04.txt"
        const val boardSize = 5
    }

    data class Board(val winningNumbers: Set<Set<Int>>)

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

    fun part1(): Int {
        for (round in 0 until (drawnNumbers.size - boardSize)) {
            for (board in boards) {
                for (numbers in board.winningNumbers) {
                    if (drawnNumbers.subList(0, round + boardSize).containsAll(numbers)) {
                        val allnumbers =
                            board.winningNumbers.flatten().toSet() - drawnNumbers.subList(0, round + boardSize).toSet()
                        return allnumbers.sum() * drawnNumbers[round + boardSize - 1]
                    }
                }
            }
        }
        return -1
    }

    fun part2(): Int {
        val winningBoards = mutableSetOf<Board>()
        for (round in 0 until (drawnNumbers.size - boardSize)) {
            for (board in boards) {
                if (board in winningBoards)
                    continue
                for (numbers in board.winningNumbers) {
                    if (drawnNumbers.subList(0, round + boardSize).containsAll(numbers)) {
                        winningBoards.add(board)
                        if (winningBoards.size == boards.size) {
                            val allnumbers =
                                board.winningNumbers.flatten().toSet() - drawnNumbers.subList(0, round + boardSize)
                                    .toSet()
                            return allnumbers.sum() * drawnNumbers[round + boardSize - 1]
                        }
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
    println("Day04::part1 = ${day04.part1()}")
    println("Day04::part2 = ${day04.part2()}")
}
