package day18.part2

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

const val w = 71
const val h = 71

fun Set<Complex>.compute(): Int? {
    data class State(val z: Complex, val n: Int)

    val hs = mutableSetOf(Complex(0, 0))
    val q = ArrayDeque(listOf(State(Complex(0, 0), 0)))
    while (q.isNotEmpty()) {
        val (z, n) = q.removeFirst()
        if (z == Complex(w - 1, h - 1)) return n
        Complex.DIRS
            .map { z + it }
            .filter { it.x in 0..<w && it.y in 0..<h && it !in this && it !in hs }
            .forEach {
                hs.add(it)
                q.add(State(it, n + 1))
            }
    }
    return null
}

fun main() {
    val l = generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .map { s -> s.split(",").map { it.toInt() } }
        .map { Complex(it[0], it[1]) }

    val hs = l.take(1024).toMutableSet()
    (l.drop(1024)).first {
        hs.add(it)
        hs.compute() == null
    }.let { println("${it.x},${it.y}") }
}
