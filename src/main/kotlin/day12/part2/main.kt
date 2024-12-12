package day12.part2

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
            .filter { z + it in hs && this[z + it] == this[z] }
            .fold(setOf(z)) { acc, d -> acc + extract(z + d) }
    }

    val l = mutableListOf<Set<Complex>>()
    while (hs.isNotEmpty()) l.add(extract(hs.first()))
    return l
}

fun Set<Complex>.rotate(): Set<Complex> {
    val x1 = this.maxOf { it.x }
    return this.map { z -> Complex(z.y, x1 - z.x) }.toSet()
}

fun Set<Complex>.countFaces(): Int {
    fun scanFromLeft(hs: Set<Complex>): Int {
        val x0 = hs.minOf { it.x } - 1
        val x1 = hs.maxOf { it.x }
        val y0 = hs.minOf { it.y }
        val y1 = hs.maxOf { it.y }

        data class Acc(var n: Int, var yPrev: Int)
        return (x0..x1).sumOf { x ->
            (y0..y1).filter { y -> Complex(x, y) !in hs && Complex(x + 1, y) in hs }
                .fold(Acc(0, y0 - 2)) { (n, yPrev), y ->
                    if (y - yPrev > 1) Acc(n + 1, y) else Acc(n, y)
                }.n
        }
    }

    return generateSequence(this) { it.rotate() }.take(4).sumOf { scanFromLeft(it) }
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .makeRegions()
        .sumOf { it.size * it.countFaces() }
        .let(::println)
