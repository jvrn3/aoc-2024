import java.io.File

fun main() {
    val grid = getGrid(File("input4.txt"))
    val word = "XMAS"
    val totalOccurrences =
        findOccurrences(grid, word) +
                findOccurrences(rotate90(grid), word) +
                findOccurrences(rotate180(grid), word) +
                findOccurrences(getVerticalLines(grid), word) +
                findOccurrences(getDiagonals(grid), word) +
                findOccurrences(getDiagonals(grid.map { it.reversed() }), word)
    println(totalOccurrences)
}


fun findOccurrences(grid: List<List<Char>>, word: String): Int {
    tailrec fun loop(line: String, position: Int, count: Int): Int {
        return when {
            position + word.length > line.length -> count
            line.substring(position, word.length + position) == word ->
                loop(line, position + 1, count + 1)

            else -> loop(line, position + 1, count)
        }
    }
    return grid.sumOf { loop(it.joinToString(""), 0, 0) }
}

fun rotate90(grid: List<List<Char>>): List<List<Char>> {
    return grid[0].indices.map { col ->
        grid.indices.map { row ->
            grid[grid.size - 1 - row][col]
        }
    }
}

fun rotate180(grid: List<List<Char>>): List<List<Char>> {
    return grid.map { row ->
        row.reversed()
    }.reversed()
}

fun getVerticalLines(grid: List<List<Char>>): List<List<Char>> {
    return grid[0].indices.map { col ->
        grid.indices.map { row ->
            grid[row][col]
        }
    }
}

// thanks copilot
fun getDiagonals(grid: List<List<Char>>): List<List<Char>> {
    val diagonals = mutableListOf<List<Char>>()
    val n = grid.size
    val m = grid[0].size

    for (k in 0 until n + m - 1) {
        val diagonal1 = mutableListOf<Char>()
        val diagonal2 = mutableListOf<Char>()
        for (j in 0..k) {
            val i = k - j
            if (i in 0 until n && j in 0 until m) {
                diagonal1.add(grid[i][j])
            }
            if (j in 0 until n && i in 0 until m) {
                diagonal2.add(grid[j][i])
            }
        }
        if (diagonal1.isNotEmpty()) diagonals.add(diagonal1)
        if (diagonal2.isNotEmpty()) diagonals.add(diagonal2)
    }
    return diagonals
}

fun getGrid(file: File): List<List<Char>> {
    return file.readLines().map { it.toList() }
}
