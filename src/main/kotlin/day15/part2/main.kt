package day15.part2

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

fun Map<Complex, Char>.move(c: Char): Map<Complex, Char> {
    val d = when (c) {
        '^' -> Complex(0, -1)
        '>' -> Complex(1, 0)
        'v' -> Complex(0, 1)
        '<' -> Complex(-1, 0)
        else -> throw IllegalStateException()
    }
    val m = this.toMutableMap()
    val seen = mutableSetOf<Complex>()
    fun move(z: Complex) {
        if (z in seen) return
        seen.add(z)
        if (m[z] == '#') return
        if (m[z] == '[') move(z + Complex(1, 0))
        if (m[z] == ']') move(z + Complex(-1, 0))
        if (m[z + d] != '.') move(z + d)
        if (m[z + d] == '.') m[z + d] = m.getValue(z).also { m[z] = '.' }
    }
    move(this.asSequence().first { it.value == '@' }.key)
    return if (m.filter { it.value == '[' }.keys.all { m[it + Complex(1, 0)] == ']' }) m else this
}

fun main() {
    val raw = generateSequence(::readLine).joinToString("\n").trim().replace("\r", "")
    val m = raw
        .split("\n\n")[0]
        .replace(".", "..")
        .replace("#", "##")
        .replace("O", "[]")
        .replace("@", "@.")
        .lines()
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()

    raw
        .split("\n\n")[1]
        .replace("\n", "")
        .fold(m) { acc, c -> acc.move(c) }
        .filter { it.value == '[' }.keys.sumOf { it.x + 100 * it.y }
        .let(::println)
}
