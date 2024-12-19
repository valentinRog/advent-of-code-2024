package day19.part2

fun String.compute(l: List<String>): Long {
    val cache = mutableMapOf<String, Long>()
    fun backtracking(s: String): Long {
        if (s in cache) return cache.getValue(s)
        if (s == "") return 1
        val n = l.filter { s.startsWith(it) }.sumOf { backtracking(s.removePrefix(it)) }
        cache[s] = n
        return n
    }
    return backtracking(this)
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
