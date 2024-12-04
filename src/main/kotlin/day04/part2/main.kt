package day04.part2

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

fun String.parse() =
    this
        .split("\n")
        .flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }
        .toMap()

fun Map<Point, Char>.offset(p: Point) = this.asIterable().associate { (k, v) -> k + p to v }

fun Map<Point, Char>.compute(): Int {
    val xMax = this.keys.maxBy { it.x }.x
    val yMax = this.keys.maxBy { it.y }.y
    return listOf(
        "M.S\n.A.\nM.S",
        "S.S\n.A.\nM.M",
        "M.M\n.A.\nS.S",
        "S.M\n.A.\nS.M",
    )
        .map { it.parse() }
        .sumOf {
            (0..xMax)
                .flatMap { x -> (0..yMax).map { y -> it.offset(Point(x, y)) } }
                .count { it.asIterable().all { (k, v) -> this[k] == v || v == '.' } }
        }
}

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .parse()
        .let { println(it.compute()) }
}
