package day09.part2

import java.math.BigInteger

fun List<Int>.compute(): List<Int> {
    val l = this.toMutableList()

    tailrec fun insertBlock(i: Int, block: IntRange) {
        if (i in block) return
        if (l[i] != -1) return insertBlock(i + 1, block)
        val space = generateSequence(i) { it + 1 }.takeWhile { l[it] == l[i] }.let { i..it.last() }
        if (space.count() < block.count()) return insertBlock(i + 1, block)
        for ((ii, jj) in space.zip(block)) l[ii] = l[jj].also { l[jj] = l[ii] }
    }

    tailrec fun compute(j: Int) {
        if (j < 0) return
        if (l[j] == -1) return compute(j - 1)
        val block = generateSequence(j) { it - 1 }.takeWhile { it >= 0 && l[it] == l[j] }.last()..j
        insertBlock(0, block)
        compute(block.first - 1)
    }

    compute(l.lastIndex)
    return l
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
        .compute()
        .withIndex()
        .filter { it.value != -1 }
        .sumOf { it.value.toBigInteger() * it.index.toBigInteger() }
        .let(::println)
