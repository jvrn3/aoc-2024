import java.io.File

const val prefix = "mul("
const val suffix = ")"

// AOC Day 3 https://adventofcode.com/2024/day/3/
// very ugly implementation not using regex 
fun main() {
    val input = getInput(File("./input3.txt"))
    println(parseMul(input))
}

fun parseMul(str: String): Int {
    tailrec fun parseTail(str: String, acc: Int): Int {
        return when {
            str.isEmpty() -> acc
            str.startsWith(prefix) -> parseTail(str.drop(1), acc + evalExpression(str))
            else -> parseTail(str.drop(1), acc)
        }
    }
    return parseTail(str, 0)
}

fun evalExpression(str: String): Int {
    val endExpressionIndex = str.indexOf(suffix, prefix.length);
    if (endExpressionIndex != -1) {
        val exp = str.substring(prefix.length, endExpressionIndex)
        if (!isValidInput(exp)) {
            return 0
        }
        if (exp.contains(",")) {
            val (left, right) = exp.split(",")
            val leftInt = left.toIntOrNull();
            val rightInt = right.toIntOrNull()

            if (leftInt != null && rightInt != null) {
                return leftInt * rightInt;
            }
        }
    }
    return 0

}

fun isValidInput(str: String): Boolean {
    for (char in str) {
        if (!char.isLetterOrDigit() && char !in listOf('(', ')', ',', 'm', 'u', 'l')) {
            return false
        }
    }
    return true
}


fun getInput(file: File): String {
    return file.readText()
}
