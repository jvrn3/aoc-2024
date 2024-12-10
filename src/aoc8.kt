import kotlin.math.abs
import java.io.File

fun main() {
    val file = File("input8.txt")
    val map = getMap(file)
    val numberOfRows = map.size - 1
    val numberOfColumns = map[0].size - 1

    println("Part 1: ${checkAntennas(getAntennas(map), numberOfRows, numberOfColumns)}")
}

fun getMap(file: File): List<List<Char>> {
    return file.readLines().map { it.toList() }
}

fun getAntennas(map: List<List<Char>>): Map<Pair<Int, Int>, Char> {
    val antennas = mutableMapOf<Pair<Int, Int>, Char>()
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] != '.') {
                antennas[i to j] = map[i][j]
            }
        }
    }
    return antennas
}

fun checkAntennas(antennas: Map<Pair<Int, Int>, Char>, numberOfRows: Int, numberOfColumns: Int): Int {
    val grouped = antennas.entries.groupBy({ it.value }, { it.key })
    val antinodes = mutableSetOf<Pair<Int, Int>>()

    grouped.forEach { (_, points) ->
        for(p1 in points){
            for(p2 in points){
                if(p1 == p2)
                    continue
                val dx = p2.first - p1.first
                val dy = p2.second - p1.second

                val anti  = p1.first - dx in 0..numberOfRows && p1.second - dy in 0..numberOfColumns
                if (anti) {
                    antinodes.add(p1.first - dx to  p1.second - dy)
                }
                
            }
            
        }
    
    }

    return antinodes.size
}
