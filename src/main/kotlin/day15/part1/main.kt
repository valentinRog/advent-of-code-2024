package day15.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

fun MutableMap<Complex, Char>.swap(k1: Complex, k2: Complex) {
    this[k1] = this.getValue(k2).also { this[k2] = this.getValue(k1) }
}

fun MutableMap<Complex, Char>.move(z: Complex, d: Complex): Complex {
    fun move(z: Complex) {
        if (this[z] == '#') return
        if (this[z + d] != '.') move(z + d)
        if (this[z + d] == '.') this.swap(z, z + d)
    }
    move(z)
    return if (this[z] == '@') z else z + d
}

fun main() {
    val raw = generateSequence(::readLine).joinToString("\n").trim().replace("\r", "")
    val m = raw
        .split("\n\n")[0]
        .lines()
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .toMutableMap()

    raw
        .split("\n\n")[1]
        .replace("\n", "")
        .map {
            when (it) {
                '^' -> Complex(0, -1)
                '>' -> Complex(1, 0)
                'v' -> Complex(0, 1)
                '<' -> Complex(-1, 0)
                else -> throw IllegalStateException()
            }
        }
        .fold(m.asSequence().first { it.value == '@' }.key) { acc, d -> m.move(acc, d) }

    m.filter { it.value == 'O' }.keys.sumOf { it.x + 100 * it.y }.let(::println)
}
