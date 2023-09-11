package at.gnu.adventofcode.year2021

class Day14(private val polymerTemplate: String, pairInsertionRulesFromInput: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day14.txt"
    }

    private val pairInsertionRules = pairInsertionRulesFromInput.map { it.split(" -> ") }
        .associateBy({ it.first() }, { Pair(it[0][0] + it.last(), it.last() + it[0][1]) })

    fun part1(): Long {
        val sumOfElements = createGeneration(10)
        return sumOfElements.values.maxOf { it } - sumOfElements.values.minOf { it }
    }

    fun part2(): Long {
        val sumOfElements = createGeneration(40)
        return sumOfElements.values.maxOf { it } - sumOfElements.values.minOf { it }
    }

    private fun createGeneration(n: Int) =
        (1..n).fold(createFirstGeneration(polymerTemplate)) { acc, _ -> acc.growPolymer() }.sumOfElements()

    private fun createFirstGeneration(polymerTemplate: String): Map<String, Long> {
        val amountOfMonomers = mutableMapOf<String, Long>()
        polymerTemplate.windowed(2, 1).forEach { amountOfMonomers.add(it, 1) }
        return amountOfMonomers.toMap()
    }

    private fun Map<String, Long>.growPolymer(): Map<String, Long> {
        val amountOfMonomers = mutableMapOf<String, Long>()
        this.keys.forEach {
            amountOfMonomers.add(pairInsertionRules[it]!!.first, this[it]!!)
            amountOfMonomers.add(pairInsertionRules[it]!!.second, this[it]!!)
        }
        return amountOfMonomers.toMap()
    }

    private fun Map<String, Long>.sumOfElements(): Map<String, Long> {
        val sum = mutableMapOf<String, Long>()
        this.keys.forEach {
            sum.add(it[0].toString(), this[it]!!)
            sum.add(it[1].toString(), this[it]!!)
        }
        sum.keys.forEach { sum[it] = (sum[it]!! + 1) / 2 }
        return sum.toMap()
    }

    private fun MutableMap<String, Long>.add(key: String, value: Long) {
        this[key] = this[key]?.plus(value) ?: value
    }
}

fun main() {
    val (polymerTemplate, pairInsertionRules) = Day14::class.java.getResource(Day14.RESOURCE)!!.readText().trim()
        .split("\n\n", "\r\n\r\n").map { it.split("\n", "\r\n") }
    val day14 = Day14(polymerTemplate.first(), pairInsertionRules)
    println("Day14::part1 -> ${day14.part1()}")
    println("Day14::part2 -> ${day14.part2()}")
}
