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
    val res = this.entries.find { starts.containsKey(it.value) }!!
    val seen = mutableSetOf<Complex>()
    tailrec fun compute(z: Complex, d: Complex) {
        if (!this.containsKey(z)) return
        if (this[z + d] == '#') return compute(z, d * Complex(0, 1))
        seen.add(z)
        return compute(z + d,  d)
    }
    compute(res.key, starts.getValue(res.value))
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
