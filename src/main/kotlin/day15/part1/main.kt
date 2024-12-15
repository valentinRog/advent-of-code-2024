package day15.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

fun MutableMap<Complex, Char>.move(c: Char) {
    val d = when (c) {
        '^' -> Complex(0, -1)
        '>' -> Complex(1, 0)
        'v' -> Complex(0, 1)
        '<' -> Complex(-1, 0)
        else -> throw IllegalStateException()
    }

    fun move(z: Complex) {
        if (this[z] == '#') return
        if (this[z + d] != '.') move(z + d)
        if (this[z + d] == '.') this[z + d] = this.getValue(z).also { this[z] = '.' }
    }
    move(this.asSequence().first { it.value == '@' }.key)
}

fun main() {
    val raw = generateSequence(::readLine).joinToString("\n").trim().replace("\r", "")
    val m = raw
        .split("\n\n")[0]
        .lines()
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .toMutableMap()

    raw.split("\n\n")[1].replace("\n", "").forEach { m.move(it) }
    m.filter { it.value == 'O' }.keys.sumOf { it.x + 100 * it.y }.let(::println)
}
