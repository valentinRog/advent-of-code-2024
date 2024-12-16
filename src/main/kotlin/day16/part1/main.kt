package day16.part1

import java.util.PriorityQueue

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
    operator fun times(other: Complex) = Complex(
        x * other.x - y * other.y,
        x * other.y + y * other.x
    )
}

fun Map<Complex, Char>.getKeyByValue(c: Char) = this.asIterable().first { it.value == c }.key

fun Map<Complex, Char>.dijkstra(): Int {
    data class P(val z: Complex, val d: Complex)
    data class Node(val p: P, val cost: Int)

    val m = mutableMapOf<P, Int>()
    val q = PriorityQueue<Node> { e1, e2 -> e1.cost.compareTo(e2.cost) }
    q.add(Node(P(this.getKeyByValue('S'), Complex(1, 0)), 0))
    val z1 = this.getKeyByValue('E')
    while (true) {
        val (p, cost) = q.poll()!!
        m[p] = cost
        val (z, d) = p
        if (z == z1) return cost
        if (this[z + d] != '#' && P(z + d, d) !in m) q.add(Node(P(z + d, d), cost + 1))
        listOf(d * Complex(0, -1), d * Complex(0, 1))
            .filter { P(z, it) !in m }
            .forEach { q.add(Node(P(z, it), cost + 1000)) }
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
        .dijkstra()
        .let(::println)
