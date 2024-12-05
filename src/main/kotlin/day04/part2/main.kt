package day04.part2

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

fun String.parse() =
    this
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }
        .toMap()

fun Map<Point, Char>.compute() =
    this
        .asIterable()
        .filter { (_, v) -> v == 'A' }
        .count { (k, _) ->
            listOf(Point(1, -1), Point(1, 1), Point(-1, 1), Point(-1, -1))
                .map { this.getOrDefault(k + it, '.') }
                .joinToString("")
                .let { listOf("SSMM", "SMMS", "MMSS", "MSSM").contains(it) }
        }

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .parse()
        .let { println(it.compute()) }
