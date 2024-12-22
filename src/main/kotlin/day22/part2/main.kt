package day22.part2

fun Long.next(): Long {
    fun Long.prune() = this % 16777216
    var n = this
    n = (n xor (n * 64)).prune()
    n = (n xor (n / 32)).prune()
    return (n xor (n * 2048)).prune()
}

fun Long.makePrices(): Map<List<Long>, Long> {
    val m = mutableMapOf<List<Long>, Long>()
    generateSequence(this) { it.next() }
        .take(2000 + 1)
        .map { it % 10 }
        .windowed(5)
        .map { l -> l.windowed(2).map { it[1] - it[0] } to l.last() }
        .forEach { (k, v) -> m.putIfAbsent(k, v) }
    return m
}

fun List<Long>.compute(): Long {
    val l = this.map { it.makePrices() }
    return l.flatMap { it.keys }.toSet().maxOf { k -> l.sumOf { it.getOrDefault(k, 0) } }
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .map { it.toLong() }
        .compute()
        .let(::println)
