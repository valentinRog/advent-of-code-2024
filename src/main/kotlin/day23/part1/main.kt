package day23.part1

fun Map<String, Set<String>>.compute(): Int {
    val hs = mutableSetOf<List<String>>()
    fun dfs(l: List<String>) {
        when (l.size) {
            3 -> if (l.any { it.startsWith("t") }) hs.add(l.sorted())
            0 -> this.keys.forEach { dfs(l + it) }
            else -> this.getValue(l[0]).filter { s -> l.all { it in this.getValue(s) } }.forEach { dfs(l + it) }
        }
    }
    dfs(emptyList())
    return hs.size
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .lines()
        .map { it.split("-") }
        .fold(mutableMapOf<String, Set<String>>()) { acc, l ->
            for (i in 0..1) acc[l[0 + i]] = acc.getOrDefault(l[0 + i], emptySet()) + l[1 - i]
            acc
        }
        .compute()
        .let(::println)
