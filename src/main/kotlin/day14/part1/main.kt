package day14.part1

import java.math.BigInteger

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

const val W = 101
const val H = 103

data class Robot(val p: Complex, val v: Complex) {
    fun move() = Robot(Complex(Math.floorMod((p.x + v.x), W), Math.floorMod((p.y + v.y), H)), v)
}

fun List<Robot>.compute(): BigInteger {
    val l = this.map { r -> generateSequence(r) { it.move() }.take(100 + 1).last() }
    return listOf(0..<W / 2, W / 2 + 1..<W).flatMap { xr ->
        listOf(0..<H / 2, H / 2 + 1..<H).map { yr ->
            l.count { it.p.x in xr && it.p.y in yr }
        }
    }.map { it.toBigInteger() }.reduce { a, b -> a * b }
}

fun String.removeAll(vararg strings: String) = strings.fold(this) { acc, s -> acc.replace(s, "") }

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .removeAll("\r", "p", "v", "=")
        .split("\n")
        .map { line -> line.split(" ", ",").map { it.toInt() } }
        .map { Robot(Complex(it[0], it[1]), Complex(it[2], it[3])) }
        .compute()
        .let(::println)
