package day09.part1

import java.math.BigInteger

fun List<Int>.compute(): List<Int> {
    val l = this.toMutableList()
    tailrec fun compute(i: Int, j: Int) {
        if (i == j) return
        if (l[i] != -1) return compute(i + 1, j)
        if (l[j] == -1) return compute(i, j - 1)
        l[i] = l[j].also { l[j] = -1 }
        compute(i + 1, j - 1)
    }
    compute(0, l.lastIndex)
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
        .filter { it != -1 }
        .withIndex()
        .sumOf { it.value.toBigInteger() * it.index.toBigInteger() }
        .let(::println)
