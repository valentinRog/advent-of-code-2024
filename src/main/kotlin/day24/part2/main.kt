package day24.part2

fun String.removeAll(vararg strings: String) = strings.fold(this) { acc, s -> acc.replace(s, "") }

fun <T> Sequence<T>.combinations() = this.flatMapIndexed { i, e1 -> this.drop(i + 1).map { e1 to it } }

fun String.op(): (Int, Int) -> Int =
    when (this) {
        "AND" -> Int::and
        "OR" -> Int::or
        "XOR" -> Int::xor
        else -> throw IllegalStateException()
    }

fun Int.ts() = if (this < 10) "0$this" else this.toString()

const val zMax = 45

fun List<List<String>>.compute(m: MutableMap<String, Int>, swaps: Map<String, String>): Long {
    val hs = this.toMutableSet()
    while (hs.isNotEmpty()) {
        val a = hs.firstOrNull { it[0] in m && it[2] in m }
        if (a == null) break
        hs.remove(a)
        m[swaps.getOrDefault(a.last(), a.last())] = a[1].op()(m.getValue(a[0]), m.getValue(a[2]))
    }
    return (0..zMax).fold(0L) { acc, i ->
        val b = m.getOrDefault("z${i.ts()}", 0)
        acc or (b.toLong() shl i)
    }
}

fun List<List<String>>.simulate(n1: Long, n2: Long, swaps: Map<String, String>) =
    listOf("x" to n1, "y" to n2)
        .flatMap { (k, n) -> (0..<zMax).map { "$k${it.ts()}" to ((n shr it) and 1).toInt() } }
        .toMap()
        .toMutableMap()
        .let { this.compute(it, swaps) }


fun List<List<String>>.compute(): String {
    fun backtracking(nBits0: Int, swaps: Map<String, String>): String? {
        if (swaps.size == 8) return swaps.keys.sorted().joinToString(",")
        val nBits =
            if (swaps.size == 6) zMax
            else generateSequence(nBits0) { it + 1 }.takeWhile { this.check(it, swaps) }.last() + 1
        for ((s1, s2) in this.map { it.last() }.filter { it !in swaps }.asSequence().combinations()) {
            val nSwaps = swaps.toMutableMap()
            nSwaps[s1] = s2
            nSwaps[s2] = s1
            if (this.check(nBits, nSwaps)) backtracking(nBits, nSwaps).let { if (it != null) return it }
        }
        return null
    }
    return backtracking(0, emptyMap())!!
}

val check: List<List<String>>.(nBits: Int, swaps: Map<String, String>) -> Boolean = run {
    val mask = (0..<zMax).fold(0L) { acc, i -> acc or ((i % 2).toLong() shl i) }
    (fun List<List<String>>.(nBits: Int, swaps: Map<String, String>): Boolean {
        val n = (1L shl nBits) - 1
        return listOf(n, n and mask, n and (mask.inv()))
            .asSequence()
            .combinations()
            .all { (n1, n2) -> this.simulate(n1, n2, swaps) == n1 + n2 }
    })
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .removeAll(":", "-> ")
        .split("\n\n")[1]
        .lines()
        .map { it.split(" ") }
        .compute()
        .let(::println)
