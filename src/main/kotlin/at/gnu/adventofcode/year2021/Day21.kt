package at.gnu.adventofcode.year2021

import kotlin.math.max

class Day21(input: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day21.txt"
        const val rollsPerRound = 3
        const val normalGameWinningScore = 1000
        const val dircacGameWinningScore = 21
        val command = """Player (\d+) starting position: (\d+)""".toRegex()
    }

    data class Game(val players: List<Player>, var player: Player, var rolls: Int = 0, var dice: Int = 0)
    data class Player(val id: Int, var position: Int = 0, var score: Int = 0)

    private val players = input.map {
        val (id, startingPosition) = command.matchEntire(it)!!.destructured
        Player(id.toInt(), startingPosition.toInt())
    }

    fun part1(): Long =
        initGame(players).playNormalGame()

    fun part2(): Long {
        val result = playDiracGame(pos1 = players.first().position, pos2 = players.last().position)
        return max(result.first, result.second)
    }

    private fun playDiracGame(pos1: Int, pos2: Int, score1: Int = 0, score2: Int = 0,
                              memo: MutableMap<String, Pair<Long, Long>> = mutableMapOf()): Pair<Long, Long> {
        memo["$pos1,$pos2,$score1,$score2"]?.let { return it }
        val wins = LongArray(2) { 0L }
        for (dice1 in 1..3) {
            for (dice2 in 1..3) {
                for (dice3 in 1..3) {
                    val newPos = ((pos1 + dice1 + dice2 + dice3 - 1) % 10) + 1
                    val newScore = score1 + newPos
                    if (newScore >= dircacGameWinningScore)
                        wins[0]++
                    else {
                        val newWins = playDiracGame(pos2, newPos, score2, newScore, memo)
                        wins[0] += newWins.second
                        wins[1] += newWins.first
                    }
                }
            }
        }
        return Pair(wins[0], wins[1]).also { memo["$pos1,$pos2,$score1,$score2"] = it }
    }

    private fun initGame(players: List<Player>): Game {
        val newPlayers = players.map { it.copy() }
        return Game(newPlayers, newPlayers.first())
    }

    private fun Game.playNormalGame(): Long {
        while (true) {
            this.playNormalRound(rollsPerRound)
            if (this.player.score >= normalGameWinningScore)
                return (this.otherPlayer().score * this.rolls).toLong()
            this.switchPlayer()
        }
    }

    private fun Game.playNormalRound(n: Int) {
        this.rolls += n
        repeat(n) {
            this.dice = if (this.dice >= 100) 1 else this.dice + 1
            this.player.position = ((this.player.position + this.dice - 1) % 10) + 1
        }
        this.player.score += this.player.position
    }

    private fun Game.switchPlayer() {
        this.player = this.otherPlayer()
    }

    private fun Game.otherPlayer(): Player =
        if (this.player === this.players.first()) this.players.last() else this.players.first()
}

fun main() {
    val input = Day21::class.java.getResource(Day21.input)!!.readText().trim().split("\n", "\r\n")
    val day21 = Day21(input)
    println("Day21::part1 -> ${day21.part1()}")
    println("Day21::part2 -> ${day21.part2()}")
}
