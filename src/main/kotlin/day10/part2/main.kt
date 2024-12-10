package day10.part2

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

fun Map<Complex, Int>.compute(): Int {
    fun compute(z: Complex): Int =
        when (this[z]) {
            9 -> 1
            else -> listOf(
                Complex(0, -1),
                Complex(1, 0),
                Complex(0, 1),
                Complex(-1, 0),
            )
                .filter { this[z + it] == this.getValue(z) + 1 }
                .sumOf { compute(z + it) }
        }
    return this.filter { it.value == 0 }.keys.sumOf { compute(it) }
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c.toString().toInt() } }
        .toMap()
        .compute()
        .let(::println)
