package day09.part2

fun MutableList<Int>.compute() {
    tailrec fun insertBlock(i: Int, block: IntRange) {
        if (i in block) return
        if (this[i] != -1) return insertBlock(i + 1, block)
        val space = generateSequence(i) { it + 1 }.takeWhile { this[it] == this[i] }.let { i..it.last() }
        if (space.count() < block.count()) return insertBlock(i + 1, block)
        for ((ii, jj) in space.zip(block)) this[ii] = this[jj].also { this[jj] = this[ii] }
    }

    tailrec fun compute(j: Int) {
        if (j < 0) return
        if (this[j] == -1) return compute(j - 1)
        val block = generateSequence(j) { it - 1 }.takeWhile { it >= 0 && this[it] == this[j] }.last()..j
        insertBlock(0, block)
        compute(block.first - 1)
    }

    compute(this.lastIndex)
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
