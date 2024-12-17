package day17.part1

fun Int.pow(n: Int) = generateSequence(this) { it * this }.take(n).last()

data class Computer(var a: Int, var b: Int, var c: Int) {
    private fun getCombo(n: Int) = when (n) {
        in 0..3 -> n
        4 -> a
        5 -> b
        6 -> c
        else -> throw IllegalArgumentException()
    }

    fun compute(l: List<Int>): List<Int> {
        val output = mutableListOf<Int>()
        var i = 0
        while (i < l.lastIndex) {
            when (l[i]) {
                0 -> a /= 2.pow(getCombo(l[i + 1]))
                1 -> b = b xor l[i + 1]
                2 -> b = getCombo(l[i + 1]) % 8
                3 -> if (a != 0) {
                    i = l[i + 1]
                    continue
                }

                4 -> b = b xor c
                5 -> output.add(getCombo(l[i + 1]).toInt() % 8)
                6 -> b = a / 2.pow(getCombo(l[i + 1]))
                7 -> c = a / 2.pow(getCombo(l[i + 1]))
                else -> throw IllegalArgumentException()
            }
            i += 2
        }
        return output
    }
}

fun main() {
    val raw = generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")

    raw
        .split("\n\n")[0]
        .lines()
        .map { it.split(" ").last().toInt() }
        .let { Computer(it[0], it[1], it[2]) }
        .compute(raw.split("\n\n")[1].split(" ").last().split(",").map { it.toInt() })
        .joinToString(",")
        .let(::println)
}