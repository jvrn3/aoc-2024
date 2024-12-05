import java.io.File

fun main() {
    val file = File("input5.txt")
    val orderingRules = getOrderingRules(file)
    val pages = getPagesToUpdate(file)
    val result = pages.filter { isInRightOrder(orderingRules, it) }.sumOf { getMiddleElement(it) }
    println(result)
}

fun isInRightOrder(orderingRules: List<Pair<Int, Int>>, page: List<Int>): Boolean {
    val zippedValues = page.zipWithNext()
    return zippedValues.all { (a, b) -> isBefore(a to b, orderingRules) }
}

fun getMiddleElement(list: List<Int>): Int {
    return list[list.size / 2]
}

fun getOrderingRules(file: File): List<Pair<Int, Int>> {
    return file.readLines().takeWhile { it.isNotBlank() }.map { line ->
        val (first, second) = line.split("|").map { it.trim().toInt() }
        first to second
    }
}

fun getPagesToUpdate(file: File): List<List<Int>> {
    return file.readLines().dropWhile { it.isNotBlank() }.drop(1).map { line ->
        line.split(",").map { it.trim().toInt() }
    }
}

fun isBefore(pageNumbers: Pair<Int, Int>, orderingRules: List<Pair<Int, Int>>): Boolean {
    return orderingRules.any { (a, b) -> a == pageNumbers.first && b == pageNumbers.second }
}