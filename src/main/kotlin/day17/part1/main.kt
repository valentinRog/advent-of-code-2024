package day17.part1

fun Int.pow(n: Int): Int = if (n == 0) 1 else this * pow(n - 1)

fun List<Int>.compute(a0: Int): List<Int> {
    var a = a0
    var b = 0
    var c = 0
    fun getCombo(n: Int) = when (n) {
        in 0..3 -> n
        4 -> a
        5 -> b
        6 -> c
        else -> throw IllegalArgumentException()
    }

    val output = mutableListOf<Int>()
    var i = 0
    while (i < this.lastIndex) {
        when (this[i]) {
            0 -> a /= 2.pow(getCombo(this[i + 1]))
            1 -> b = b xor this[i + 1]
            2 -> b = getCombo(this[i + 1]) % 8
            3 -> if (a != 0) {
                i = this[i + 1]
                continue
            }

            4 -> b = b xor c
            5 -> output.add(getCombo(this[i + 1]) % 8)
            6 -> b = a / 2.pow(getCombo(this[i + 1]))
            7 -> c = a / 2.pow(getCombo(this[i + 1]))
            else -> throw IllegalArgumentException()
        }
        i += 2
    }
    return output
}

fun main() {
    val raw = generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")

    val a0 = raw
        .split("\n\n")[0]
        .lines()
        .first()
        .split(" ")
        .last()
        .toInt()

    raw
        .split("\n\n")[1]
        .split(" ")
        .last()
        .split(",")
        .map { it.toInt() }
        .compute(a0)
        .let(::println)
}