package day22.part1

fun Long.next(): Long {
    val m = 16777216
    var n = this
    n = (n xor (n * 64)) % m
    n = (n xor (n / 32)) % m
    return (n xor (n * 2048)) % m
}

fun Long.compute() = (1..2000).fold(this) { n, _ -> n.next() }

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .sumOf { it.toLong().compute() }
        .let(::println)
