package day13.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

data class Machine(val a: Complex, val b: Complex, val target: Complex) {
    fun compute(): Int? {
        val xf = target.x
        val yf = target.y
        val d = a.x * b.y - a.y * b.x
        val da = xf * b.y - yf * b.x
        val db = a.x * yf - a.y * xf
        if (db % d != 0 || da % d != 0) return null
        val a = da / d
        val b = db / d
        return 3 * a + b
    }
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n\n")
        .map { s ->
            s.split("\n").map { line ->
                line
                    .split(":")[1]
                    .split(", ", "+", "=")
                    .filterIndexed { i, _ -> i % 2 == 1 }
                    .map { it.toInt() }
            }.let {
                Machine(
                    Complex(it[0][0], it[0][1]),
                    Complex(it[1][0], it[1][1]),
                    Complex(it[2][0], it[2][1])
                )
            }
        }.mapNotNull { it.compute() }
        .sumOf { it }
        .let(::println)
