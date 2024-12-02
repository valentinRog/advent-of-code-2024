package day02

fun valid(l: List<Int>) =
    (l.sorted() in arrayOf(l, l.reversed())) && l.zipWithNext().all { (a, b) -> a - b in (-3..-1) + (1..3) }

fun main() {
    generateSequence(::readLine).joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { it.split(" ").map(String::toInt) }
        .map { l -> listOf(l) + l.indices.map { l.filterIndexed { i, _ -> i != it } } }
        .count { it.any(::valid) }
        .let(::println)
}
