package at.gnu.adventofcode.year2021

import java.lang.Long.parseLong

class Day16(transmission: String) {

    companion object {
        const val input = "/adventofcode/year2021/Day16.txt"
    }

    private val binary = transmission.map { it.toBinary() }.joinToString("")

    fun part1(): Long {
        val message = Message.from(binary)
        message.parse()
        return message.sumOfVersions()
    }

    fun part2(): Long {
        val message = Message.from(binary)
        message.parse()
        return message.evaluate()
    }

    private fun Char.toBinary(): String {
        return when (this) {
            '0' -> "0000"
            '1' -> "0001"
            '2' -> "0010"
            '3' -> "0011"
            '4' -> "0100"
            '5' -> "0101"
            '6' -> "0110"
            '7' -> "0111"
            '8' -> "1000"
            '9' -> "1001"
            'A' -> "1010"
            'B' -> "1011"
            'C' -> "1100"
            'D' -> "1101"
            'E' -> "1110"
            'F' -> "1111"
            else -> throw RuntimeException("unable to convert '$this' to binary")
        }
    }

    class Message(private val version: Int, private val typeId: Int, private val content: String) {

        private var value = 0L
        private val subMessages = mutableListOf<Message>()

        companion object {
            fun from(binary: String) =
                Message(Integer.parseInt(binary.substring(0, 3), 2),
                    Integer.parseInt(binary.substring(3, 6), 2), binary.substring(6))
        }

        fun parse(): String =
            when (typeId) {
                4 -> {
                    val result = parseLiteral(content)
                    value = parseLong(result.first, 2)
                    result.second
                }
                else -> parseOperator(content)
            }

        fun evaluate(): Long {
            return when (typeId) {
                0 -> subMessages.sumOf { it.evaluate() }
                1 -> subMessages.fold(1) { acc, it -> acc * it.evaluate() }
                2 -> subMessages.minOf { it.evaluate() }
                3 -> subMessages.maxOf { it.evaluate() }
                4 -> value
                5 -> if (subMessages.first().evaluate() > subMessages.last().evaluate()) 1 else 0
                6 -> if (subMessages.first().evaluate() < subMessages.last().evaluate()) 1 else 0
                7 -> if (subMessages.first().evaluate() == subMessages.last().evaluate()) 1 else 0
                else -> throw RuntimeException("unknown packet type '$typeId'")
            }
        }

        fun sumOfVersions(): Long {
            var sum = version.toLong()
            for (subMessage in subMessages)
                sum += subMessage.sumOfVersions()
            return sum
        }

        private fun parseLiteral(content: String): Pair<String, String> {
            var last = false
            return content.chunked(5).fold(Pair("0", "")) { acc, it ->
                if (last)
                    Pair(acc.first, acc.second + it)
                else {
                    if (it[0] == '0')
                        last = true
                    Pair(acc.first + it.substring(1), acc.second)
                }
            }
        }

        private fun parseOperator(content: String): String =
            when (content[0]) {
                '0' -> {
                    val length = content.substring(1, 16).toDecimal()
                    var subContent = content.substring(16, 16 + length)
                    while (subContent.isNotEmpty()) {
                        val subMessage = from(subContent)
                        subMessages.add(subMessage)
                        subContent = subMessage.parse()
                    }
                    content.substring(16 + length)
                }
                else -> {
                    val length = content.substring(1, 12).toDecimal()
                    var subContent = content.substring(12)
                    repeat(length) {
                        val subMessage = from(subContent)
                        subMessages.add(subMessage)
                        subContent = subMessage.parse()
                    }
                    subContent
                }
            }

        private fun String.toDecimal() =
            Integer.parseInt(this, 2)
    }
}

fun main() {
    val day16 = Day16(Day16::class.java.getResource(Day16.input)!!.readText().trim())
    println("Day16::part1 -> ${day16.part1()}")
    println("Day16::part2 -> ${day16.part2()}")
}
