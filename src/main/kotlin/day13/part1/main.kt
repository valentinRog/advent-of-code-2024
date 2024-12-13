package day13.part1

data class Complex(var x: Int, var y: Int) {
    operator fun plus(other: Complex) = Complex(x + other.x, y + other.y)
}

data class Machine(val a: Complex, val b: Complex, val target: Complex) {
    fun compute(): Int? {
        val cache = mutableMapOf<Complex, Int>()
        fun compute(z: Complex, cost: Int): Int? {
            if (z in cache && cache.getValue(z) <= cost) return null
            cache[z] = cost
            if (z == target) return cost
            if (z.x > target.x || z.y > target.y) return null
            return listOfNotNull(
                compute(z + a, cost + 3),
                compute(z + b, cost + 1),
            ).minOrNull()
        }
        return compute(Complex(0, 0), 0)
    }
}

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .split("\n\n")
        .map { s ->
            s.split("\n").map { line ->
                line
                    .split(":")[1]
                    .split(", ", "+", "=")
                    .filterIndexed { i, _ -> i % 2 == 1 }
                    .map { it.toInt() }
            }.let {
                Machine(
                    Complex(it[0][0], it[0][1]),
                    Complex(it[1][0], it[1][1]),
                    Complex(it[2][0], it[2][1])
                )
            }
        }.mapNotNull { it.compute() }
        .sum()
        .let(::println)
}