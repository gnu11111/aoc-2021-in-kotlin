/**
 * ADVENT OF CODE 2021 - DAY 2: DIVE!
 *
 * Now, you need to figure out how to pilot this thing.
 *
 * It seems like the submarine can take a series of commands like forward 1,
 * down 2, or up 3:
 *
 *   - forward X increases the horizontal position by X units.
 *   - down X increases the depth by X units.
 *   - up X decreases the depth by X units.
 *
 * Note that since you're on a submarine, down and up affect your depth, and
 * so they have the opposite result of what you might expect.
 *
 * The submarine seems to already have a planned course (your puzzle input).
 * You should probably figure out where it's going. For example:
 *
 *   forward 5
 *   down 5
 *   forward 8
 *   up 3
 *   down 8
 *   forward 2
 *
 * Your horizontal position and depth both start at 0. The steps above would
 * then modify them as follows:
 *
 *   - forward 5 adds 5 to your horizontal position, a total of 5.
 *   - down 5 adds 5 to your depth, resulting in a value of 5.
 *   - forward 8 adds 8 to your horizontal position, a total of 13.
 *   - up 3 decreases your depth by 3, resulting in a value of 2.
 *   - down 8 adds 8 to your depth, resulting in a value of 10.
 *   - forward 2 adds 2 to your horizontal position, a total of 15.
 *
 * After following these instructions, you would have a horizontal position of
 * 15 and a depth of 10. (Multiplying these together produces 150.)
 *
 * Calculate the horizontal position and depth you would have after following
 * the planned course. What do you get if you multiply your final horizontal
 * position by your final depth?
 *
 * --- Part Two ---
 *
 * Based on your calculations, the planned course doesn't seem to make any
 * sense. You find the submarine manual and discover that the process is
 * actually slightly more complicated.
 *
 * In addition to horizontal position and depth, you'll also need to track a
 * third value, aim, which also starts at 0. The commands also mean something
 * entirely different than you first thought:
 *
 *   - down X increases your aim by X units.
 *   - up X decreases your aim by X units.
 *   - forward X does two things:
 *       o It increases your horizontal position by X units.
 *       o It increases your depth by your aim multiplied by X.
 *
 * Again note that since you're on a submarine, down and up do the opposite of
 * what you might expect: "down" means aiming in the positive direction.
 *
 * Now, the above example does something different:
 *
 *   - forward 5 adds 5 to your horizontal position, a total of 5. Because
 *     your aim is 0, your depth does not change.
 *   - down 5 adds 5 to your aim, resulting in a value of 5.
 *   - forward 8 adds 8 to your horizontal position, a total of 13. Because
 *     your aim is 5, your depth increases by 8 * 5 = 40.
 *   - up 3 decreases your aim by 3, resulting in a value of 2.
 *   - down 8 adds 8 to your aim, resulting in a value of 10.
 *   - forward 2 adds 2 to your horizontal position, a total of 15. Because
 *     your aim is 10, your depth increases by 2 * 10 = 20 to a total of 60.
 *
 * After following these new instructions, you would have a horizontal
 * position of 15 and a depth of 60. (Multiplying these produces 900.)
 *
 * Using this new interpretation of the commands, calculate the horizontal
 * position and depth you would have after following the planned course. What
 * do you get if you multiply your final horizontal position by your final
 * depth?
 */
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
