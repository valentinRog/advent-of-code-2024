package day17.part2

import kotlin.math.min

fun Long.pow(n: Long): Long = if (n == 0L) 1L else this * pow(n - 1)

fun List<Int>.simulate(a0: Long): List<Int> {
    var a = a0
    var b = 0L
    var c = 0L
    fun getCombo(n: Int) = when (n) {
        in 0..3 -> n.toLong()
        4 -> a
        5 -> b
        6 -> c
        else -> throw IllegalArgumentException()
    }

    val output = mutableListOf<Int>()
    var i = 0
    while (i < this.lastIndex) {
        when (this[i]) {
            0 -> a /= 2L.pow(getCombo(this[i + 1]))
            1 -> b = b xor this[i + 1].toLong()
            2 -> b = getCombo(this[i + 1]) % 8
            3 -> if (a != 0L) {
                i = this[i + 1]
                continue
            }

            4 -> b = b xor c
            5 -> output.add((getCombo(this[i + 1]) % 8).toInt())
            6 -> b = a / 2L.pow(getCombo(this[i + 1]))
            7 -> c = a / 2L.pow(getCombo(this[i + 1]))
            else -> throw IllegalArgumentException()
        }
        i += 2
    }
    return output
}

fun List<Int>.compute(): Long {
    fun backtracking(a: Long, k: Int): Long? {
        val l = this.simulate(a)
        if (l == this) return a
        if (l.size > this.size || k > 15) return null
        if (l.subList(0, min(k, l.lastIndex)) != this.subList(0, k)) return null
        for (n1 in 0L..<(1 shl 3)) {
            for (n2 in 0L..<(1 shl 3)) {
                val res = backtracking(a or (n1 shl (k * 3) or (n2 shl (k * 3 + (n1 xor 1).toInt()))), k + 1)
                if (res != null) return res
            }
        }
        return null
    }
    return backtracking(0, 0)!!
}

fun main() =
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n\n")[1]
        .split(" ")
        .last()
        .split(",")
        .map { it.toInt() }
        .compute()
        .let(::println)
