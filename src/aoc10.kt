import java.io.File
import java.util.*

data class Vertex(val id: String, val value: Int)
data class Edge(val from: Vertex, val to: Vertex)
typealias  Graph = Map<Vertex, List<Edge>>

fun main(){
    val file = File("input10_test.txt")

    val graph = generateGraph(readData(file))
    println(getScores(graph))
}

fun addVertex(graph: Graph, vertex: Vertex): Graph{
    return graph + (vertex to emptyList())
}

fun addEdge(graph: Graph, edge: Edge): Graph{
    val edges = graph[edge.from]?.toMutableList() ?: mutableListOf()
    edges.add(edge)
    return graph + (edge.from to edges)
}

fun readData (file: File): List<List<Int>>{
    return file.readLines().map { line -> line.map { char -> char.toString().toInt() } }
}

fun searchScores(graph: Graph, current: Vertex):Int{
        val visited = mutableSetOf<Vertex>()
        val queue: Queue<Vertex> = LinkedList()
        queue.add(current)
        while(queue.isNotEmpty()){
            val vertex = queue.poll()
            visited.add(vertex)
            graph[vertex]?.forEach { edge ->
                if(edge.to !in visited){
                    queue.add(edge.to)
                }
            }
        }
    
    return visited.count{it.value == 9}
}

fun getScores(graph: Graph): Int{
    return graph.keys.sumOf { vertex ->
        // starting point
        if (vertex.value == 0) searchScores(graph, vertex)
        else 0
    }

}


fun generateGraph(data: List<List<Int>>): Graph{
    var graph: Graph = emptyMap()
    for (i in data.indices){
        for (j in data[i].indices){
            val vertex = Vertex("${i}${j}", data[i][j])
            graph = addVertex(graph, vertex)
            if(j - 1 >= 0){
                val value = data[i][j - 1]
                if(vertex.value +1== value){
                    graph = addEdge(graph, Edge(vertex, Vertex("${i}${j-1}", value)))
                }
            }
            if(j + 1 < data[i].size){
                val value = data[i][j + 1]
                if(vertex.value +1== value){
                    graph = addEdge(graph, Edge(vertex, Vertex("${i}${j+1}", value)))
                }
            }
            if(i+1 < data[i].size){
                val value = data[i+1][j]
                if(vertex.value + 1 == value ){
                    graph = addEdge(graph, Edge(vertex, Vertex("${i+1}${j}", value)))
                }
                
            }
            if(i-1 >= 0){
                val value = data[i-1][j]
                if(vertex.value + 1 == value ){
                    graph = addEdge(graph, Edge(vertex, Vertex("${i-1}${j}", value)))
                }
            }
          
         
        }
    }
    return graph
}
