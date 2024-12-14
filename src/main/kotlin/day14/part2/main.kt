package day14.part2

import kotlin.math.abs

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

const val W = 101
const val H = 103

data class Robot(val p: Complex, val v: Complex) {
    fun move() = Robot(Complex(Math.floorMod((p.x + v.x), W), Math.floorMod((p.y + v.y), H)), v)
}

fun List<Robot>.next() = this.map { it.move() }

fun List<Robot>.density(): Int {
    val ps = this.map { it.p }
    return ps.sumOf { r -> ps.sumOf { (x, y) -> abs(r.x - x) + abs(r.y - y) } }
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
        .let { l -> generateSequence(l) { it.next() }.takeWhile { it.density() > 10_000_000 }.count() }
        .let(::println)
