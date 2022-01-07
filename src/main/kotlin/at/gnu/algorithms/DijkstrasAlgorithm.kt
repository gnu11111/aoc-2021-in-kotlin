package at.gnu.algorithms

import kotlin.math.min

class DijkstrasAlgorithm(private val field: List<List<Point>>) {

    companion object {
        val outOfBounds = Point(-1, -1, Int.MAX_VALUE)
    }

    class Point(val x: Int, val y: Int, val cost: Int)

    fun calculateCostOfCheapestPath(from: Point, to: Point, minCost: Int = Integer.MAX_VALUE, cost: Int = 0,
                                    path: Set<Point> = emptySet()): Int =
        when {
            (from === outOfBounds) || (from in path) -> minCost
            (from === to) -> min(cost, minCost)
            else -> from.adjacentPoints().fold(minCost) { acc, it ->
                calculateCostOfCheapestPath(it, to, acc, cost + it.cost, path + from)
            }
        }

    private fun Point.adjacentPoints(): List<Point> =
        listOf(pointAt(x - 1, y), pointAt(x + 1, y), pointAt(x, y - 1), pointAt(x, y + 1))

    private fun pointAt(x: Int, y: Int): Point =
        field.elementAtOrNull(y)?.elementAtOrNull(x) ?: outOfBounds
}

fun main() {
    val mat = listOf(
        listOf(1, 1, 1, 9, 1, 0, 0, 1, 1, 1),
        listOf(0, 7, 1, 9, 1, 2, 0, 1, 0, 1),
        listOf(0, 0, 1, 0, 1, 1, 1, 0, 0, 1),
        listOf(1, 0, 1, 1, 1, 0, 1, 1, 0, 1),
        listOf(0, 0, 0, 1, 0, 0, 0, 1, 0, 1),
        listOf(1, 0, 1, 1, 1, 0, 0, 1, 1, 0),
        listOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 1),
        listOf(0, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        listOf(1, 1, 1, 1, 1, 0, 0, 1, 1, 1),
        listOf(0, 0, 1, 0, 0, 1, 1, 0, 0, 1),
    )
    val field = mat.mapIndexed { y, line ->
        line.mapIndexed { x, cost ->
            if (cost > 0) DijkstrasAlgorithm.Point(x, y, cost) else DijkstrasAlgorithm.outOfBounds
        }
    }
    val from = field.first().first()
    val to = field.last().last()
    val minCost = DijkstrasAlgorithm(field).calculateCostOfCheapestPath(from, to)
    if (minCost < Int.MAX_VALUE)
        println("The cheapest path from ${from.x}/${from.y} to ${to.x}/${to.y} costs $minCost")
    else
        println("Destination ${to.x}/${to.y} cannot be reached from source ${from.x}/${from.y}")
}
