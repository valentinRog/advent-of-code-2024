package day19.part1

fun String.valid(l: List<String>): Boolean {
    val cache = mutableSetOf<String>()
    fun backtracking(s: String): Boolean {
        if (s in cache) return false
        cache.add(s)
        if (s == this) return true
        if (!this.startsWith(s)) return false
        return l.any { backtracking(s + it) }
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
    arr[1].lines().count { it.valid(l) }.let(::println)
}
