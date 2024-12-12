package day08.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
    operator fun minus(other: Complex) = Complex(x - other.x, y - other.y)
}

fun Map<Complex, Char>.compute() =
    this
        .asSequence()
        .filter { it.value != '.' }
        .flatMap { e -> this.map { listOf(e, it) } }
        .filter { it[0].value == it[1].value && it[0].key != it[1].key }
        .map { it[0].key to it[1].key }
        .flatMap { (z1, z2) -> listOf(z1 + (z1 - z2), z2 + (z2 - z1)) }
        .filter { it in this }
        .toSet().size

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .compute()
        .let(::println)

