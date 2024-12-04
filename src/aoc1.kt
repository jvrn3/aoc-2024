import java.io.File
import kotlin.math.abs

// AOC Day 1 https://adventofcode.com/2024/day/1/
fun main(){
    val file  = File("./input1.txt")

    val listOfPair = pairSmallest(getListOfPairs(file));

    println("The sum is " + sumDistance(listOfPair))
    print("The similarity score is " + getSimilarityScore(listOfPair.map{it.first}, listOfPair.map { it.second }))
}
// part 2
fun getSimilarityScore(first: List<Int>, second: List<Int>): Int{
    return first.sumOf{element -> element * second.filter{ it == element}.size}
}

fun getListOfPairs (file: File): List<Pair<Int,Int>>{
    return file.readLines().map{line ->
        val (first,second) = line.split("   ").map { it.trim().toInt() }
        first to second
    }
}

fun pairSmallest(listOfPair: List<Pair<Int, Int>>): List<Pair<Int, Int>>{
    val leftPairSorted = listOfPair.map{it.first }.sorted()
    val rightPairSorted = listOfPair.map{it.second }.sorted()
    return leftPairSorted.zip(rightPairSorted);
}
fun sumDistance(listOfPair: List<Pair<Int, Int>>): Int {
    return listOfPair.sumOf { abs(it.second - it.first) };
}
