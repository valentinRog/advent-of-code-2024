package day25.part1

data class Complex(var x: Int, var y: Int)

fun <T> Sequence<T>.combinations() = this.flatMapIndexed { i, e1 -> this.drop(i + 1).map { e1 to it } }

fun Pair<Map<Complex, Char>, Map<Complex, Char>>.fit() =
    !this.first.asSequence().any { (k, v) -> v == '#' && this.second[k] == '#' }

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n\n")
        .map { it.lines().flatMapIndexed { y, line -> line.mapIndexed { x, c -> Complex(x, y) to c } }.toMap() }
        .asSequence()
        .combinations()
        .count { it.fit() }
        .let(::println)
