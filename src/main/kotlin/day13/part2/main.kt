package day13.part2

import java.math.BigInteger

data class Complex(var x: BigInteger, var y: BigInteger) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

data class Machine(val a: Complex, val b: Complex, val target: Complex) {
    fun compute(): BigInteger? {
        val xf = target.x
        val yf = target.y
        val d = a.x * b.y - a.y * b.x
        val da = xf * b.y - yf * b.x
        val db = a.x * yf - a.y * xf
        if (db % d != 0.toBigInteger() || da % d != 0.toBigInteger()) return null
        val a = da / d
        val b = db / d
        return 3.toBigInteger() * a + b
    }
}

fun main() {
    val n = 10000000000000.toBigInteger()
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
                    .map { it.toBigInteger() }
            }.let {
                Machine(
                    Complex(it[0][0], it[0][1]),
                    Complex(it[1][0], it[1][1]),
                    Complex(it[2][0] + n, it[2][1] + n)
                )
            }
        }.mapNotNull { it.compute() }
        .sumOf { it }
        .let(::println)
}