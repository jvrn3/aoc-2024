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

data class Vector(val x: Int, val y: Int){
    operator fun plus(other: Vector): Vector{
        return Vector(x + other.x, y + other.y)
    }
    operator fun minus(other: Vector): Vector{
        return Vector(x - other.x, y - other.y)
    }
    fun isInRange(rows: Int, columns: Int): Boolean{
        return x in 0..rows && y in 0..columns
    }
}

fun getAntennas(map: List<List<Char>>): Map<Char, List<Vector>> {
    val antennas = mutableMapOf<Char,MutableList<Vector>>()
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] != '.') {
                antennas.computeIfAbsent(map[i][j]) { mutableListOf() }.add(Vector(i, j))
            }
        }
    }
    return antennas
}

fun checkAntennas(antennas: Map<Char, List<Vector>>, numberOfRows: Int, numberOfColumns: Int): Int {
    val antinodes = mutableSetOf<Vector>()
    antennas.forEach { (_, points) ->
        for(p1 in points){
            for(p2 in points){
                if(p1 == p2) continue
                val difference = p2 - p1
                
                if ((p2+difference).isInRange(numberOfRows, numberOfColumns) )
                    antinodes.add(p2+difference)
                
            }
        }
    }

    return antinodes.size
}
