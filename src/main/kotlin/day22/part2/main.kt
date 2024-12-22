package day22.part2

fun <T, U> Sequence<T>.uniqueBy(selector: (T) -> U): Sequence<T> {
    val seen = mutableSetOf<U>()
    return this.filter {
        if (selector(it) in seen) return@filter false
        seen.add(selector(it))
        true
    }
}

fun Long.next(): Long {
    val m = 16777216
    var n = this
    n = (n xor (n * 64)) % m
    n = (n xor (n / 32)) % m
    return (n xor (n * 2048)) % m
}

fun Long.makePrices() =
    generateSequence(this) { it.next() }
        .take(2000 + 1)
        .map { (it % 10).toInt() }
        .windowed(5)
        .map { l -> l.windowed(2).map { it[1] - it[0] } to l.last() }

fun List<Long>.compute() =
    this
        .map { it.makePrices() }
        .fold(mutableMapOf<List<Int>, Int>()) { acc, l ->
            l.uniqueBy { it.first }.forEach { (k, v) -> acc[k] = acc.getOrDefault(k, 0) + v }
            acc
        }.values
        .max()

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .map { it.toLong() }
        .compute()
        .let(::println)
