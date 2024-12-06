package day06.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
    operator fun times(other: Complex) = Complex(
        x * other.x - y * other.y,
        x * other.y + y * other.x
    )
}

fun Map<Complex, Char>.compute(): Int {
    val starts = mapOf(
        '^' to Complex(0, -1),
        '>' to Complex(1, 0),
        'v' to Complex(0, 1),
        '<' to Complex(-1, 0),
    )
    var (z, c) = this.entries.find { starts.containsKey(it.value) }!!
    val seen = mutableSetOf<Complex>()
    var d = starts.getValue(c)
    while (this.containsKey(z)) {
        if (this[z + d] == '#') {
            d *= Complex(0, 1)
            continue
        }
        seen.add(z)
        z += d
    }
    return seen.size
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }
        .toMap()
        .compute()
        .let(::println)
