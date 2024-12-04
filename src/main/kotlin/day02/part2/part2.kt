package day02

import kotlin.math.abs

fun valid(l: List<Int>) =
    (l.sorted() in arrayOf(l, l.reversed())) && l.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 }

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { it.split(" ").map(String::toInt) }
        .map { l -> listOf(l) + l.indices.map { l.filterIndexed { i, _ -> i != it } } }
        .count { it.any(::valid) }
        .let(::println)
}
