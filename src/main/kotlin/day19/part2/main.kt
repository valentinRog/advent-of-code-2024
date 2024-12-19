package day19.part2

fun String.compute(l: List<String>): Long {
    val cache = mutableMapOf<String, Long>()
    fun backtracking(s: String): Long {
        if (s == this) return 1
        if (!this.startsWith(s)) return 0
        val rs = this.removePrefix(s)
        if (rs in cache) return cache.getValue(rs)
        val n = l.sumOf { backtracking(s + it) }
        cache[rs] = n
        return n
    }
    return backtracking("")
}

fun main() {
    val arr = generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n\n")
    val l = arr[0].split(", ")
    arr[1].lines().sumOf { it.compute(l) }.let(::println)
}
