package day20.part2

import kotlin.math.abs

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
    fun manhattan(other: Complex) = abs(this.x - other.x) + abs(this.y - other.y)

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

fun Map<Complex, Char>.makeDistanceMatrix(): Map<Complex, Int> {
    val m = mutableMapOf<Complex, Int>()
    tailrec fun f(z: Complex, zp: Complex?, n: Int) {
        m[z] = n
        if (this[z] == 'S') return
        val d = Complex.DIRS.first { this[z + it] != '#' && z + it != zp }
        return f(z + d, z, n + 1)
    }
    f(this.getKeyByValue('E'), null, 0)
    return m
}

fun Map<Complex, Char>.makeCheats(m: Map<Complex, Int>): Map<Complex, List<Complex>> {
    val cheats = mutableMapOf<Complex, List<Complex>>()
    val l = this.filter { it.value in ".ES".toList() }.keys
    for (z in l) cheats[z] = l.filter { z.manhattan(it) <= 20 && m.getValue(z) > m.getValue(it) }
    return cheats
}

fun Map<Complex, Char>.compute(): Int {
    val m = this.makeDistanceMatrix()
    val cheats = this.makeCheats(m)
    val cheatCost = mutableMapOf<Pair<Complex, Complex>, Int>()
    tailrec fun dfs(z: Complex, zp: Complex?, cost: Int) {
        if (this[z] == 'E') return
        cheats.getValue(z).forEach { cheatCost[z to it] = m.getValue(it) + cost + z.manhattan(it) }
        val d = Complex.DIRS.first { this[z + it] != '#' && z + it != zp }
        dfs(z + d, z, cost + 1)
    }
    dfs(this.getKeyByValue('S'), null, 0)
    val n = m.getValue(this.getKeyByValue('S'))
    return cheatCost.values.count { n - it >= 100 }
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
