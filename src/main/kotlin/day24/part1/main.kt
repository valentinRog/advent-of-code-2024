package day24.part1

fun String.removeAll(vararg strings: String) = strings.fold(this) { acc, s -> acc.replace(s, "") }

fun String.op(): (Int, Int) -> Int =
    when (this) {
        "AND" -> Int::and
        "OR" -> Int::or
        "XOR" -> Int::xor
        else -> throw IllegalStateException()
    }

fun Int.ts() = if (this < 10) "0$this" else this.toString()

const val zMax = 45

fun List<List<String>>.compute(m: MutableMap<String, Int>): Long {
    val hs = this.toMutableSet()
    while (hs.isNotEmpty()) {
        val a = hs.firstOrNull { it[0] in m && it[2] in m }
        if (a == null) break
        hs.remove(a)
        m[a.last()] = a[1].op()(m.getValue(a[0]), m.getValue(a[2]))
    }
    return (0..zMax).fold(0L) { acc, i ->
        val b = m.getOrDefault("z${i.ts()}", 0)
        acc or (b.toLong() shl i)
    }
}

fun main() {
    val a = generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .removeAll(":", "-> ")
        .split("\n\n")
    val m = a[0]
        .lines()
        .map { it.split(" ") }
        .associate { it[0] to it[1].toInt() }
        .toMutableMap()
    a[1]
        .lines()
        .map { it.split(" ") }
        .compute(m)
        .let(::println)
}