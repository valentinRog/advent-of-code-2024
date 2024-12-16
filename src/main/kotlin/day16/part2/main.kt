package day16.part2

import java.util.PriorityQueue

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
    operator fun times(other: Complex) = Complex(
        x * other.x - y * other.y,
        x * other.y + y * other.x
    )
}

fun Map<Complex, Char>.getKeyByValue(c: Char) = this.asIterable().first { it.value == c }.key

fun Map<Complex, Char>.compute(): Int {
    data class P(val z: Complex, val d: Complex)
    data class Node(val p: P, val cost: Int, val path: List<Complex>)

    val m = mutableMapOf<P, Int>()
    val q = PriorityQueue<Node> { e1, e2 -> e1.cost.compareTo(e2.cost) }
    val z0 = this.getKeyByValue('S')
    q.add(Node(P(z0, Complex(1, 0)), 0, listOf(z0)))
    val z1 = this.getKeyByValue('E')
    while (true) {
        val (p, cost, path) = q.poll()!!
        m[p] = cost
        val (z, d) = p
        if (z == z1) {
            val hs = path.toMutableSet()
            generateSequence { q.poll()!! }
                .takeWhile { q.isNotEmpty() && it.p.z == z1 && it.cost == cost }
                .forEach { hs.addAll(it.path) }
            return hs.size
        }
        if (this[z + d] != '#' && P(z + d, d) !in m) q.add(Node(P(z + d, d), cost + 1, path + (z + d)))
        listOf(d * Complex(0, -1), d * Complex(0, 1))
            .filter { P(z, it) !in m }
            .forEach { q.add(Node(P(z, it), cost + 1000, path)) }
    }
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .compute()
        .let(::println)
