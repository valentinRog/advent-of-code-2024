package day09.part1

fun MutableList<Int>.compute() {
    tailrec fun compute(i: Int, j: Int) {
        if (i == j) return
        if (this[i] != -1) return compute(i + 1, j)
        if (this[j] == -1) return compute(i, j - 1)
        this[i] = this[j].also { this[j] = -1 }
        compute(i + 1, j - 1)
    }
    compute(0, this.lastIndex)
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .toList()
        .withIndex()
        .fold(emptyList<Int>()) { acc, (i, c) ->
            acc + generateSequence { if (i % 2 == 0) i / 2 else -1 }.take(c.toString().toInt()).toList()
        }
        .toMutableList()
        .also { it.compute() }
        .withIndex()
        .filter { it.value != -1 }
        .sumOf { it.value.toBigInteger() * it.index.toBigInteger() }
        .let(::println)
