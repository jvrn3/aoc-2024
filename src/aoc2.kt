import java.io.File
import kotlin.math.abs

fun main(){
    val reports = getReports(File("./input2.txt"))
    val validReports = reports.filter { isIncreasingOrDecreasingAndDistanceAllowed(it) }
    println(validReports.size)
}

fun isIncreasingOrDecreasingAndDistanceAllowed(report: List<Int>): Boolean{
    val zippedReports = report.zipWithNext()
    val isIncreasing = zippedReports.all { (a, b) -> a < b}
    val isDecreasing = zippedReports.all { (a, b) -> a > b}
    val distanceAllowed = zippedReports.all { (a, b) -> abs(b - a) in 1..3 }
    return (isIncreasing || isDecreasing) && distanceAllowed
}

fun getReports(file: File): List<List<Int>>{
    return file.readLines().map{line -> line.split(" ").map { it.toInt() }}
}