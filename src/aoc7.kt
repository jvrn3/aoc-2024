import java.io.File
import java.math.BigInteger

data class TreeNode(val value: Int, val left: TreeNode?, val right: TreeNode?)
data class Equation(val result: BigInteger, val numbers: List<Int>)

fun main() {
    val equations = getEquations(File("input7.txt"))
    val part1 = equations.filter{search(buildTree(it.numbers), it.result)}.sumOf { it.result }
    println("Part 1: $part1")
}

fun buildTree(numbers: List<Int>): TreeNode {
    if (numbers.size == 1) {
        return TreeNode(numbers.first(), null, null)
    }
    return TreeNode(numbers.first(), buildTree(numbers.drop(1)), buildTree(numbers.drop(1)))
}

fun getEquations(file: File): List<Equation> {
    return file.readLines().map { line -> 
        val (result, numbers) = line.split(": ")
        Equation(result.toBigInteger(), numbers.split(" ").map { it.trim().toInt() })
    }
}

/*
         81 
        +/\*
       40   40 
      +/\*  +/\*
     27 27  27 27 
     /\  /\  /\ /\ 
 
 148 1161   3267  87840
 */
fun search(tree: TreeNode, expected: BigInteger): Boolean {
    fun loop(node: TreeNode, sum: BigInteger): Boolean {
        if (node.left == null) {
            return sum == expected
        }
        if (node.right == null) {
            return sum == expected
        }
        return loop(node.left, sum + node.left.value.toBigInteger())
                || loop(node.right, sum * node.right.value.toBigInteger())
       
    }
    return loop(tree, tree.value.toBigInteger())
}