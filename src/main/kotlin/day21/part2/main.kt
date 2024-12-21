package day21.part2

import java.math.BigInteger
import java.util.ArrayDeque

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)

    fun dirToC() = when (Complex(x, y)) {
        Complex(0, -1) -> '^'
        Complex(1, 0) -> '>'
        Complex(0, 1) -> 'v'
        Complex(-1, 0) -> '<'
        else -> throw IllegalStateException()
    }

    companion object {
        val DIRS = listOf(
            Complex(0, -1),
            Complex(1, 0),
            Complex(0, 1),
            Complex(-1, 0),
        )
    }
}

fun Map<Complex, Char>.getKeyByValue(c: Char) = this.asIterable().first { it.value == c }.key

fun String.parsePad() =
    this
        .lines()
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .filter { (_, c) -> c != '.' }
        .toMap()

val numPad = """
    789
    456
    123
    .0A
"""
    .trimIndent()
    .parsePad()

val dirPad = """
    .^A
    <v>
"""
    .trimIndent()
    .parsePad()

fun Map<Complex, Char>.bfs(z0: Complex, z1: Complex): List<List<Char>> {
    data class Node(val z: Complex, val path: List<Char>)

    val hs = mutableSetOf<Complex>()
    val q = ArrayDeque(listOf(Node(z0, emptyList())))
    while (true) {
        val (z, path) = q.pop()
        if (z == z1) {
            val l = mutableListOf(path)
            while (q.isNotEmpty()) {
                val (nz, nPath) = q.pop()
                if (nPath.size > path.size) break
                if (nz == z1) l.add(nPath)
            }
            return l
        }
        Complex.DIRS
            .filter { z + it in this && z + it !in hs }
            .forEach { q.add(Node(z + it, path + it.dirToC())) }
    }
}

fun String.compute(): BigInteger {
    val cache = mutableMapOf<Pair<String, Int>, BigInteger>()
    fun dfs(s: String, l: List<Map<Complex, Char>>, i: Int): BigInteger {
        if (s to i in cache) return cache.getValue(s to i)
        if (i == l.size) return s.length.toBigInteger()
        var z = l[i].getKeyByValue('A')
        val n = s.sumOf { c ->
            val z1 = l[i].getKeyByValue(c)
            val n = l[i].bfs(z, z1).map { it + 'A' }.minOf { dfs(it.joinToString(""), l, i + 1) }
            z = z1
            n
        }
        cache[s to i] = n
        return n
    }

    return dfs(this, listOf(numPad) + (1..25).map { dirPad }, 0)
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .sumOf { it.compute() * it.substring(0..<it.lastIndex).toBigInteger() }
        .let(::println)
