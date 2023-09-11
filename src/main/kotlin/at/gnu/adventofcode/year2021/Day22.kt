package at.gnu.adventofcode.year2021

class Day22(input: List<String>) {

    companion object {
        const val RESOURCE = "/adventofcode/year2021/Day22.txt"
        val command = """(on|off)\s+x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)""".toRegex()
        val boundary = BootStep(region = Region(-50..50, -50..50, -50..50))
    }

    private val rebootSteps = input.map {
        val (onOff, x1, x2, y1, y2, z1, z2) = command.matchEntire(it)?.destructured ?: error("error parsing '$it'")
        BootStep((onOff == "on"), Region(x1.toInt()..x2.toInt(), y1.toInt()..y2.toInt(), z1.toInt()..z2.toInt()))
    }

    fun part1(): Long =
        calculateNumberOfBootedCubes(rebootSteps.filter { (it intersect boundary) != null })

    fun part2(): Long =
        calculateNumberOfBootedCubes()

    private fun calculateNumberOfBootedCubes(bootSteps: List<BootStep> = rebootSteps): Long {
        val bootedSteps = mutableListOf<BootStep>()
        bootSteps.forEach { step ->
            bootedSteps.addAll(bootedSteps.mapNotNull { it intersect step })
            if (step.on) bootedSteps.add(step)
        }
        return bootedSteps.sumOf { it.cubes() }
    }

    class BootStep(val on: Boolean = true, private val region: Region) {

        fun cubes(): Long =
            if (on) region.cubes() else -region.cubes()

        infix fun intersect(other: BootStep): BootStep? {
            val intersection = region intersect other.region
            return if (intersection == null)
                null
            else
                return BootStep(!on, intersection)
        }
    }

    class Region(private val xRange: IntRange, private val yRange: IntRange, private val zRange: IntRange) {

        fun cubes(): Long =
            (xRange.last - xRange.first + 1L) * (yRange.last - yRange.first + 1) * (zRange.last - zRange.first + 1)

        infix fun intersect(other: Region): Region? {
            val xIntersection = xRange intersect other.xRange
            val yIntersection = yRange intersect other.yRange
            val zIntersection = zRange intersect other.zRange
            return if (xIntersection.isEmpty() || yIntersection.isEmpty() || zIntersection.isEmpty())
                null
            else
                Region(xIntersection, yIntersection, zIntersection)
        }

        private infix fun IntRange.intersect(other: IntRange) =
            maxOf(this.first, other.first)..minOf(this.last, other.last)
    }
}

fun main() {
    val input = Day22::class.java.getResource(Day22.RESOURCE)!!.readText().trim().split("\n", "\r\n")
    val day22 = Day22(input)
    println("Day22::part1 -> ${day22.part1()}")
    println("Day22::part2 -> ${day22.part2()}")
}
