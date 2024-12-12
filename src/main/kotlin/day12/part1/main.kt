package day12.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)

    companion object {
        val DIRS = listOf(
            Complex(0, -1),
            Complex(1, 0),
            Complex(0, 1),
            Complex(-1, 0),
        )
    }
}

fun Map<Complex, Char>.makeRegions(): List<Set<Complex>> {
    val hs = this.keys.toMutableSet()
    fun extract(z: Complex): Set<Complex> {
        hs.remove(z)
        return Complex.DIRS
            .filter { hs.contains(z + it) && this[z + it] == this[z] }
            .fold(setOf(z)) { acc, d -> acc + extract(z + d) }
    }

    val l = mutableListOf<Set<Complex>>()
    while (hs.isNotEmpty()) l.add(extract(hs.first()))
    return l
}

fun Set<Complex>.compute() = this.size * this.sumOf { z -> Complex.DIRS.count { !this.contains(z + it) } }

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .makeRegions()
        .sumOf { it.compute() }
        .let(::println)
}