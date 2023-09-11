package at.gnu.adventofcode.year2021

class Day18(private val numbers: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day18.txt"
        const val THRESHOLD = 4
        private val snailFishNumber = """\[(\d+),(\d+)""".toRegex()
    }

    fun part1(): Int =
        numbers.fold("") { acc, number -> acc.add(number).reduceNumber() }.calculateMagnitude()

    fun part2(): Int =
        numbers.indices.map { i ->
            numbers.indices.map { j ->
                if (i != j)
                    numbers[i].add(numbers[j]).reduceNumber().calculateMagnitude()
                else
                    0
            }
        }.flatten().maxOf { it }

    private fun String.add(number: String): String =
        if (this.isBlank()) number else "[$this,$number]"

    private fun String.reduceNumber(): String {
        var number = this
        var brackets = 0
        var i = -1
        while (++i < (number.length - 1)) {
            if (number[i] == '[') {
                if (++brackets > THRESHOLD) {
                    val newNumber = number.explode(i)
                    if (newNumber != number) {
                        number = newNumber
//                      println("after explode: $number")
                    }
                    i = -1
                    brackets = 0
                }
            } else if (number[i] == ']')
                --brackets
        }
        val newSplitNumber = number.splitNumber()
        if (newSplitNumber != number) {
//          println("after split:   $newSplitNumber")
            return newSplitNumber.reduceNumber()
        }
        return number
    }

    private fun String.explode(start: Int): String {
        val end = start + this.substring(start).indexOf(']')
        val (first, second) = snailFishNumber.matchEntire(this.substring(start, end))!!.destructured
        val left = this.substring(0, start).addToLastNumberLeft(first)
        val right = this.substring(end + 1).addToFirstNumberRight(second)
        return left + "0" + right
    }

    private fun String.splitNumber(): String {
        var start = -1
        for (i in this.indices) {
            if ((this[i] in '0'..'9') && (start < 0))
                start = i
            else if ((this[i] !in '0'..'9') && (start >= 0)) {
                val number = this.substring(start, i).toInt()
                if (number >= 10)
                    return this.substring(0, start) + "[" + (number / 2) + "," + ((number + 1) / 2) + "]" +
                            this.substring(i)
                start = -1
            }
        }
        return this
    }

    private fun String.addToLastNumberLeft(number: String): String {
        var start = -1
        var end = -1
        for (i in (this.length - 1) downTo 0) {
            if ((this[i] in '0'..'9') && (end < 0))
                end = i + 1
            else if ((this[i] !in '0'..'9') && (end >= 0)) {
                start = i + 1
                break
            }
        }
        return if (start < 0)
            this
        else
            this.substring(0, start) + (this.substring(start, end).toInt() + number.toInt()) + this.substring(end)
    }

    private fun String.addToFirstNumberRight(number: String): String =
        this.replace("""(\D*?)(\d+)(.*)""".toRegex()) {
            it.groupValues[1] + (it.groupValues[2].toInt() + number.toInt()) + it.groupValues[3]
        }

    private fun String.calculateMagnitude(): Int {
        var brackets = 0
        var i = -1
        var max = -1
        var start = -1
        while (++i < (this.length - 1)) {
            if (this[i] == '[') {
                if (++brackets > max) {
                    max = brackets
                    start = i
                }
            } else if (this[i] == ']')
                --brackets
        }
        if (start >= 0) {
            val end = start + this.substring(start).indexOf("]")
            val numbers = this.substring(start + 1, end).split(",").map { it.toInt() }
//          println("magnitude: ${this.substring(0, start) + ((numbers.first() * 3) + (numbers.last() * 2)) +
//                  this.substring(end + 1)}")
            return (this.substring(0, start) + ((numbers.first() * 3) + (numbers.last() * 2)) +
                    this.substring(end + 1)).calculateMagnitude()
        }
        return this.toInt()
    }
}

fun main() {
    val numbers = Day18::class.java.getResource(Day18.RESOURCE)!!.readText().trim().split("\n", "\r\n")
    val day18 = Day18(numbers)
    println("Day18::part1 -> ${day18.part1()}")
    println("Day18::part2 -> ${day18.part2()}")
}
