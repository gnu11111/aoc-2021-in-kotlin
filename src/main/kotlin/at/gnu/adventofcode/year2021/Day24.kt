package at.gnu.adventofcode.year2021

class Day24(source: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day24.txt"
        const val digits = 14
        const val w = "w"
        const val x = "x"
        const val y = "y"
        const val z = "z"
        val statement = """(inp|add|mul|div|mod|eql)\s+([wxyz])(\s+(-?\d+|[wxyz]))*""".toRegex()
    }

    sealed class Statement
    class Inp(val a: String) : Statement()
    class Add(val a: String, val b: String, val value: Int? = null) : Statement()
    class Mul(val a: String, val b: String, val value: Int? = null) : Statement()
    class Div(val a: String, val b: String, val value: Int? = null) : Statement()
    class Mod(val a: String, val b: String, val value: Int? = null) : Statement()
    class Eql(val a: String, val b: String, val value: Int? = null) : Statement()

    class Result(val line: Int = 0, val value: Int = 0)
    class Transition(val digit: Int = 0, val previousValue: Int = 0, val value: Int = 0) {
        override fun hashCode(): Int = value.hashCode()
        override fun equals(other: Any?): Boolean = (value == (other as Transition).value)
    }

    private val statements = source.map {
        val (statement, a, _, b) = statement.matchEntire(it)!!.destructured
        val value = b.toIntOrNull()
        when (statement) {
            "inp" -> Inp(a)
            "add" -> Add(a, b, value)
            "mul" -> Mul(a, b, value)
            "div" -> Div(a, b, value)
            "mod" -> Mod(a, b, value)
            "eql" -> Eql(a, b, value)
            else -> throw RuntimeException("don't know how to handle '$it'")
        }
    }

    fun part1(): Long =
        statements.executeCode().calculateModelNumber()

    fun part2(): Long =
        statements.executeCode(1..9).calculateModelNumber()

    private fun List<Statement>.executeCode(digitRange: IntProgression = 9 downTo 1): Map<Int, Set<Transition>> {
        val transitions = mutableMapOf<Int, MutableSet<Transition>>()
        transitions[0] = mutableSetOf(Transition())
        var result = Result()
        var runFromLine = 0
        for (i in 1..digits) {
            transitions[i] = mutableSetOf()
            for (previousTransition in transitions[i-1]!!)
                for (digit in digitRange) {
                    result = this.executeCodeUntilNextInput(digit, runFromLine, previousTransition.value)
                    transitions[i]!!.add(Transition(digit, previousTransition.value, result.value))
                }
            runFromLine = result.line
//          println("digit: $i, line: $runFromLine, unique results: ${transitions[i]!!.size}")
        }
        return transitions.toMap()
    }

    private fun List<Statement>.executeCodeUntilNextInput(input: Int, runFromLine: Int = 0,
                                                          initialZvalue: Int = 0): Result {
        val r = mutableMapOf(w to 0, x to 0, y to 0, z to initialZvalue)
        var firstInp = true
        for (line in runFromLine until this.size) {
            when (val it = this[line]) {
                is Inp -> if (firstInp) { r[it.a] = input; firstInp = false } else return Result(line, r[z]!!)
                is Add -> r[it.a] = r[it.a]!! + (it.value ?: r[it.b]!!)
                is Mul -> r[it.a] = r[it.a]!! * (it.value ?: r[it.b]!!)
                is Div -> r[it.a] = r[it.a]!! / (it.value ?: r[it.b]!!)
                is Mod -> r[it.a] = r[it.a]!! % (it.value ?: r[it.b]!!)
                is Eql -> r[it.a] = if (r[it.a]!! == (it.value ?: r[it.b]!!)) 1 else 0
            }
        }
        return Result(this.size, r[z]!!)
    }

    private fun Map<Int, Set<Transition>>.calculateModelNumber(): Long {
        var value = 0
        var power = 1L
        var modelNumber = 0L
        for (i in 0 until digits) {
            val transition = this[digits-i]!!.firstOrNull { it.value == value } ?: return -1L
            value = transition.previousValue
            modelNumber += (power * transition.digit)
            power *= 10
        }
        return modelNumber
    }
}

fun main() {
    val source = Day24::class.java.getResource(Day24.input)!!.readText().trim().split("\n", "\r\n")
    val day24 = Day24(source)
    println("Day24::part1 -> ${day24.part1()}")
    println("Day24::part2 -> ${day24.part2()}")
}
