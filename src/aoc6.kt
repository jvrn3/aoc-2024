import java.io.File

fun main() {
    val map = readMap(File("input6.txt"))
    println("Part 1: ${count(map)}")
  
}

fun printMap(map: List<List<Char>>){
    println(map.joinToString("\n").replace(",", ""))
    println()
}

fun readMap(file: File): MutableList<MutableList<Char>> {
    return file.readLines().map { it.toMutableList() }.toMutableList()
}

fun shouldTurnRight(map: List<List<Char>>, currentLocation: Pair<Int, Int>): Boolean {
    val (x, y) = getNextMovePosition(map, currentLocation)
    return map[x][y] == '#'
    
}
fun leavesMap(map: List<List<Char>>, currentLocation: Pair<Int, Int>): Boolean{
    val (x, y) =currentLocation
    val numberOfRows = map.indices.last
    val numberOfColumns = map[0].indices.last
    return when(map[x][y]){
        '>' ->  y+1 > numberOfColumns 
        '<' ->  y -1 < 0
        'v' ->  x +1 > numberOfRows 
        '^' ->  x- 1 < 0
        else -> false
    }
}

fun getNextMovePosition(map: List<List<Char>>, currentLocation: Pair<Int, Int>): Pair<Int, Int>{
    val (x,y) = currentLocation
    return when(map[x][y]){
        '>' -> x to y+1
        '<' -> x to y-1
        '^' -> x-1 to y
        'v' -> x+1 to y
        else -> 0 to 0
    } 
}



fun count(map: MutableList<MutableList<Char>>): Int{
    val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var currentLocation = getGuardPosition(map)?: return 0
    visited.add(currentLocation)
    while(!leavesMap(map, currentLocation)){
        if (shouldTurnRight(map, currentLocation)) {
            turnRight(map, currentLocation)
        }

        currentLocation = moveForward(map, currentLocation)
        if(currentLocation !in visited){
            visited.add(currentLocation)
        }
        // for debugging
        //printMap(map)
    }
    return visited.size
}

fun getGuardPosition(map: List<List<Char>>): Pair<Int, Int>? {
    map.forEachIndexed { i, row ->
        row.forEachIndexed { j, cell ->
            if (cell in listOf('>', '^', '<', 'v'))
                return i to j
        }
    }


    return null
}

fun turnRight(map: MutableList<MutableList<Char>>, currentLocation: Pair<Int, Int>) {
    val (x, y) = currentLocation
    when (map[x][y]) {
        '>' -> {map[x][y] = 'v'}
        'v' -> map[x][y] = '<'
        '<' -> map[x][y] = '^'
        '^' -> map[x][y] = '>'
    }
}

fun moveForward(map: MutableList<MutableList<Char>>, currentLocation: Pair<Int, Int>): Pair<Int,Int> {
    val(x,y) = currentLocation
    val (nextX, nextY)  = getNextMovePosition(map, currentLocation)
    map[nextX][nextY] = map[x][y]
    map[x][y] = 'X'
    return nextX to nextY
}