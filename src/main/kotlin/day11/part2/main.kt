package day11.part2

import java.math.BigInteger

class Solver {
    private val cache = mutableMapOf<Pair<BigInteger, Int>, BigInteger>()

    fun compute(n: BigInteger, depth: Int): BigInteger {
        if (n to depth in cache) return cache.getValue(n to depth)
        if (depth == 0) return 1.toBigInteger()
        if (n == 0.toBigInteger()) return cache.getOrPut(n to depth) { compute(1.toBigInteger(), depth - 1) }
        if (n.toString().length % 2 == 0) {
            val n1 = n.toString().substring(0, n.toString().length / 2).toBigInteger()
            val n2 = n.toString().substring(n.toString().length / 2).toBigInteger()
            return cache.getOrPut(n to depth) { compute(n1, depth - 1) + compute(n2, depth - 1) }
        }
        return cache.getOrPut(n to depth) { compute(n * 2024.toBigInteger(), depth - 1) }
    }
}

fun main() {
    val solver = Solver()
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split(" ")
        .map { it.toBigInteger() }
        .sumOf { solver.compute(it, 75) }
        .let(::println)
}
