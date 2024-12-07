package day07.part1

import java.math.BigInteger

fun List<BigInteger>.valid(): Boolean {
    fun compute(n: BigInteger, i: Int): Boolean {
        if (n == this[0]) return true
        if (n > this[0] || i == this.size) return false
        return listOf(n + this[i], n * this[i]).any { compute(it, i + 1) }
    }
    return compute(this[1], 2)
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .replace(":", "")
        .split("\n")
        .map { it.split(" ").map(String::toBigInteger) }
        .filter { it.valid() }
        .sumOf { it[0] }
        .let(::println)
