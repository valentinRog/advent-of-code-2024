package day20.part1

import java.util.PriorityQueue

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

fun Map<Complex, Char>.getKeyByValue(c: Char) = this.asIterable().first { it.value == c }.key

fun Map<Complex, Char>.cheats() =
    this
        .filter { it.value in listOf('.', 'E', 'S') }
        .keys
        .flatMap { z ->
            Complex.DIRS.filter { this[z + it] == '#' && this[z + it + it] in listOf('.', 'E', 'S') }
                .map { z to z + it + it }
        }

fun Map<Complex, Char>.dijkstra(): Int {

    fun dijkstra(cheat: Pair<Complex, Complex>?): Int {
        data class Node(val z: Complex, val cost: Int)

        val m = mutableMapOf<Complex, Int>()
        val q = PriorityQueue<Node> { e1, e2 -> e1.cost.compareTo(e2.cost) }
        q.add(Node(this.getKeyByValue('S'), 0))
        val z1 = this.getKeyByValue('E')
        while (true) {
            val (z, cost) = q.poll()!!
            m[z] = cost
            if (z == z1) return cost
            if (cheat != null && z == cheat.first && cheat.second !in m) {
                q.add(Node(cheat.second, cost + 2))
            } else {
                Complex.DIRS.map { z + it }.filter { this[it] != '#' && it !in m }
                    .forEach { q.add(Node(it, cost + 1)) }
            }
        }

    }

    val n = dijkstra(null)
    return this.cheats().count { n - dijkstra(it) >= 100 }
}

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .dijkstra()
        .let(::println)
}