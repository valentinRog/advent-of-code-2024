package day04.part1

data class Point(val x: Int, val y: Int)

fun Map<Point, Char>.compute(): Int {
    val xMax = this.keys.maxBy { it.x }.x
    val yMax = this.keys.maxBy { it.y }.y

    return listOf(
        (0..yMax).map { y -> (0..xMax).map { this[Point(it, y)] } },
        (0..xMax).map { x -> (0..yMax).map { this[Point(x, it)] } },
        (0..xMax).map { x -> (0..yMax).map { this[Point(x + it, it)] } },
        (0..xMax).map { x -> (0..yMax).map { this[Point(x - it, it)] } }
    )
        .flatten()
        .sumOf { l ->
            l
                .windowed(4)
                .map { it.joinToString("") }
                .count { "XMAS" in listOf(it, it.reversed()) }
        }
}

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { ".".repeat(it.length) + it + ".".repeat(it.length) }
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }
        .toMap()
        .let { println(it.compute()) }
}
