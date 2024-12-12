package day05.part2

fun Map<Int, Set<Int>>.valid(l: List<Int>) =
    l
        .withIndex()
        .flatMap { e -> l.withIndex().filter { e.index < it.index }.map { e.value to it.value } }
        .all { (a, b) -> b in this.getValue(a) }

fun main() {
    val raw = generateSequence(::readLine).joinToString("\n").trim().replace("\r", "")
    val m = raw
        .split("\n\n")
        .first()
        .split("\n")
        .map { it.split("|").map(String::toInt) }
        .fold(emptyMap<Int, Set<Int>>()) { acc, a ->
            acc +
                    mapOf(a[0] to (acc.getOrDefault(a[0], emptySet()) + a[1])) +
                    mapOf(a[1] to (acc.getOrDefault(a[1], emptySet())))
        }

    raw
        .split("\n\n")[1]
        .split("\n")
        .map { it.split(",").map(String::toInt) }
        .filter { !m.valid(it) }
        .map { it.sortedWith { a, b -> if (b in m.getValue(a)) -1 else 1 } }
        .sumOf { l -> l[(l.size - 1) / 2] }
        .let(::println)
}