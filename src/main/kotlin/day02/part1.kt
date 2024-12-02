package day02

import kotlin.math.abs

fun main() {
    generateSequence(::readLine).joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { it.split(" ").map(String::toInt) }
        .filter { it.sorted() in arrayOf(it, it.reversed()) }
        .count { l -> l.zipWithNext().all { (a, b) -> abs(a - b) in (1..3) } }
        .let(::println)
}
