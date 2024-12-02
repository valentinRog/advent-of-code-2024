package day02

fun main() {
    generateSequence(::readLine).joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { it.split(" ").map(String::toInt) }
        .filter { it.sorted() in  arrayOf(it, it.reversed()) }
        .count { l -> l.zipWithNext().all { (a, b) -> a - b in (-3..-1) + (1..3) } }
        .let(::println)
}
