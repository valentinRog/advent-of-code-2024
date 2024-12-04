package day04.part1

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

fun Map<Point, Char>.compute() =
    this
        .filterValues { it == 'X' }
        .asIterable()
        .sumOf { (k, v) ->
            listOf(
                Point(0, -1),
                Point(1, -1),
                Point(1, 0),
                Point(1, 1),
                Point(0, 1),
                Point(-1, 1),
                Point(-1, 0),
                Point(-1, -1),
            ).count {
                (0..3).fold(k to "") { acc, _ ->
                    (acc.first + it) to (acc.second + this.getOrDefault(acc.first, '.'))
                }.second == "XMAS"
            }
        }

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }
        .toMap()
        .let { println(it.compute()) }
}
