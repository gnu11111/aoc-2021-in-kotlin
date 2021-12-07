package at.gnu.adventofcode.year2021

class Day02(commandLines: List<String>) {

    companion object {
        const val input = "/adventofcode/year2021/Day02.txt"
    }

    private val commands: List<Command> = commandLines.map { Command(it) }

    enum class NavigationMethod { NORMAL, USE_AIM }
    data class Submarine(val horizontalPosition: Int = 0, val depth: Int = 0, val aim: Int = 0)

    fun part1(): Int {
        val (horizontalPosition, depth) = Submarine().navigateUsing(NavigationMethod.NORMAL)
        return (horizontalPosition * depth)
    }

    fun part2(): Int {
        val (horizontalPosition, depth) = Submarine().navigateUsing(NavigationMethod.USE_AIM)
        return (horizontalPosition * depth)
    }

    private fun Submarine.navigateUsing(method: NavigationMethod): Submarine =
        commands.fold(this) { submarine, command -> command.navigate(submarine, method) }

    sealed class Command(val navigate: (Submarine, NavigationMethod) -> Submarine)
    private fun Command(line: String): Command {
        val (command, amount) = line.split("""\s""".toRegex())
        return when (command) {
            "forward" -> Forward(amount.toInt())
            "up" -> Up(amount.toInt())
            "down" -> Down(amount.toInt())
            else -> error("no such command: $command")
        }
    }

    class Forward(private val amount: Int) : Command({ it, method ->
        when (method) {
            NavigationMethod.NORMAL -> Submarine(it.horizontalPosition + amount, it.depth, 0)
            NavigationMethod.USE_AIM -> Submarine(it.horizontalPosition + amount, it.depth + (it.aim * amount), it.aim)
        }
    })

    class Up(private val amount: Int) : Command({ it, method ->
        when (method) {
            NavigationMethod.NORMAL -> Submarine(it.horizontalPosition, it.depth - amount, 0)
            NavigationMethod.USE_AIM -> Submarine(it.horizontalPosition, it.depth, it.aim - amount)
        }
    })

    class Down(private val amount: Int) : Command({ it, method ->
        when (method) {
            NavigationMethod.NORMAL -> Submarine(it.horizontalPosition, it.depth + amount, 0)
            NavigationMethod.USE_AIM -> Submarine(it.horizontalPosition, it.depth, it.aim + amount)
        }
    })
}

fun main() {
    val input = Day02::class.java.getResource(Day02.input)!!.readText().trim().split("\n", "\r\n")
    val day02 = Day02(input)
    println("Day02::part1 -> ${day02.part1()}")
    println("Day02::part2 -> ${day02.part2()}")
}
