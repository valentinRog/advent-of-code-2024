package day20.part1

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

fun Map<Complex, Char>.compute(): Int {
    val m = this.makeDistanceMatrix()
    val cheats = mutableMapOf<Pair<Complex, Complex>, Int>()
    tailrec fun dfs(z: Complex, zp: Complex?, cost: Int) {
        if (this[z] == 'E') return
        val d = Complex.DIRS.first { this[z + it] != '#' && z + it != zp }
        Complex
            .DIRS
            .filter { this[z + it] == '#' && this[z + it + it] in ".ES".toList() }
            .map { z + it + it }
            .forEach { cheats[z to it] = m.getValue(it) + cost + 2 }
        dfs(z + d, z, cost + 1)
    }
    dfs(this.getKeyByValue('S'), null, 0)
    val n = m.getValue(this.getKeyByValue('S'))
    return cheats.values.count { n - it >= 100 }
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
