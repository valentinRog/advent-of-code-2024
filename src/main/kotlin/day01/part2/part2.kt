package day01.part2

fun main() {
    val data = generateSequence(::readLine).joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n")
        .map { s -> s.split(' ').filter { it.isNotEmpty() }.map { it.toInt() } }
        .map { it[0] to it[1] }
        .unzip()
    val m = data.second.groupingBy { it }.eachCount()
    data.first.sumOf { it * m.getOrDefault(it, 0) }.let(::println)
}
