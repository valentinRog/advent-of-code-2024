package day03.part1

enum class TokenName {
    MUl,
    INT,
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,
    COMMA,
    OTHER,
}

class Token(val tokenName: TokenName, val s: String?)

class Lexer(private val s: String) {
    private var i = 0

    private val m = mapOf(
        "mul" to Token(TokenName.MUl, null),
        "(" to Token(TokenName.OPEN_PARENTHESIS, null),
        ")" to Token(TokenName.CLOSE_PARENTHESIS, null),
        "," to Token(TokenName.COMMA, null),
    )

    fun done() = i >= s.length

    fun nextToken(): Token {
        for ((k, v) in m) {
            if (s.startsWith(k, i)) {
                i += k.length
                return v
            }
        }
        if (s[i].isDigit()) {
            val tokenS = s.drop(i).takeWhile(Char::isDigit)
            i += tokenS.length
            return Token(TokenName.INT, tokenS)
        }
        i++
        return Token(TokenName.OTHER, null)
    }
}

fun lex(s: String): List<Token> {
    val tokens = mutableListOf<Token>()
    val lexer = Lexer(s)
    while (!lexer.done()) tokens.add(lexer.nextToken())
    return tokens
}

fun compute(l: List<Token>): Int {
    var res = 0
    tailrec fun compute(i: Int) {
        if (i + 5 >= l.size) return
        if (l.slice(i..i + 5).map { it.tokenName } == listOf(
                TokenName.MUl,
                TokenName.OPEN_PARENTHESIS,
                TokenName.INT,
                TokenName.COMMA,
                TokenName.INT,
                TokenName.CLOSE_PARENTHESIS
            )) {
            res += listOf(l[i + 2], l[i + 4]).map { it.s!!.toInt() }.let { it[0] * it[1] }
            return compute(i + 6)
        }
        return compute(i + 1)
    }
    compute(0)
    return res
}

fun main() {
    generateSequence(::readLine)
        .joinToString("\n")
        .trim()
        .replace("\r", "")
        .let { println(compute(lex(it))) }
}