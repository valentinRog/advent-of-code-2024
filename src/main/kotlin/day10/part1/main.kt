package day10.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

fun Map<Complex, Int>.compute(): Int {
    fun compute(z: Complex): Set<Complex> =
        when (this[z]) {
            9 -> setOf(z)
            else -> listOf(
                Complex(0, -1),
                Complex(1, 0),
                Complex(0, 1),
                Complex(-1, 0),
            )
                .filter { this[z + it] == this.getValue(z) + 1 }
                .fold(emptySet<Complex>()) { acc, d -> acc + compute(z + d) }
        }
    return this.filter { it.value == 0 }.keys.sumOf { compute(it).size }
}

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> Complex(x, y) to c.toString().toInt() }
        }
        .toMap()
        .compute()
        .let(::println)
}
