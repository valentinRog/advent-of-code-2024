package day01.part1

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { s -> s.split(' ').filter { it.isNotEmpty() }.map { it.toInt() } }
        .map { it[0] to it[1] }
        .unzip()
        .toList()
        .map { it.sorted() }
        .let { it[0].zip(it[1]) }
        .sumOf { (a, b) -> kotlin.math.abs(a - b) }
        .let(::println)

