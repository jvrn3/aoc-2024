import java.io.File

fun main() {
    val file = File("input5.txt")
    val orderingRules = getOrderingRules(file)
    val pages = getPagesToUpdate(file)
    val part1 = pages.filter { isInRightOrder(orderingRules, it) }.sumOf { getMiddleElement(it) }
    val part2 = pages.filter { !isInRightOrder(orderingRules, it) }.map { putInCorrectOrder(orderingRules, it) }.sumOf { getMiddleElement(it) }
    println("Part 1: $part1")
    println("Part 2: $part2")
}

fun isInRightOrder(orderingRules: List<Pair<Int, Int>>, page: List<Int>): Boolean {
    val zippedValues = page.zipWithNext()
    return zippedValues.all { (a, b) -> isBefore(a to b, orderingRules) }
}

fun putInCorrectOrder(orderingRules: List<Pair<Int, Int>>, page: List<Int>): List<Int>{
    val mutablePage = page.toMutableList()
    while(!isInRightOrder(orderingRules, mutablePage)){
        val zippedValues = mutablePage.zipWithNext()
        val notOrderedPair = zippedValues.first { (a, b) -> !isBefore(a to b, orderingRules) }
        val index = mutablePage.indexOf(notOrderedPair.first)
        val swap = mutablePage[index]
        mutablePage[index] = mutablePage[index + 1]
        mutablePage[index+1] = swap
    }
    return mutablePage

    
    
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