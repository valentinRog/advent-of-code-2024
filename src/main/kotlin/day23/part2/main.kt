package day23.part2

fun Map<String, Set<String>>.compute(): String {
    val cache = mutableMapOf<List<String>, List<String>>()
    fun dfs(hs: List<String>): List<String> {
        if (hs in cache) return cache.getValue(hs)
        var res = hs
        val l = if (hs.isNotEmpty()) this.getValue(hs.first()) else this.keys
        l.filter { s -> hs.all { it in this.getValue(s) } }.forEach { s ->
            res = listOf(res, dfs((hs + s).sorted())).maxBy { it.size }
        }
        cache[hs] = res
        return res
    }
    return dfs(emptyList()).joinToString(",")
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
