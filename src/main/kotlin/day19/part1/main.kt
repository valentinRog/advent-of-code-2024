package day19.part1

fun String.valid(l: List<String>): Boolean {
    val cache = mutableSetOf<String>()
    fun backtracking(s: String): Boolean {
        if (s in cache) return false
        cache.add(s)
        if (s == "") return true
        return l.filter { s.startsWith(it) }.any { backtracking(s.removePrefix(it)) }
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
    arr[1].lines().count { it.valid(l) }.let(::println)
}
