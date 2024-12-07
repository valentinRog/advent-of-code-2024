package day06.part2

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
    operator fun times(other: Complex) = Complex(
        x * other.x - y * other.y,
        x * other.y + y * other.x
    )
}

sealed class TraverseResult {
    class Out(val path: Set<Complex>) : TraverseResult()
    data object Loop : TraverseResult()
}

fun Map<Complex, Char>.traverse(wall: Complex?): TraverseResult {
    var (z, c) = this.entries.find { it.value == '^' }!!
    val seen = mutableSetOf<Pair<Complex, Complex>>()
    var d = Complex(0, -1)
    while (true) {
        if (!this.containsKey(z)) return TraverseResult.Out(seen.map { it.first }.toSet())
        if (this[z + d] == '#' || z + d == wall) {
            d *= Complex(0, 1)
            continue
        }
        if (seen.contains(z to d)) return TraverseResult.Loop
        seen.add(z to d)
        z += d
    }
}

fun Map<Complex, Char>.compute() =
    this
        .traverse(null)
        .let { it as TraverseResult.Out }.path
        .count { this.traverse(it) is TraverseResult.Loop }

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
