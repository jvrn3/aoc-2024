import java.math.BigInteger
import kotlin.math.pow

data class Stone(val number: BigInteger){
    fun evenNumberOfDigits() = numberLength() % 2 == 0
    
    fun split(): List<Stone>{
        val divisor = 10.0.pow(numberLength()/2).toInt()
        val firstPart = number.div(divisor.toBigInteger())
        val secondPart = number.remainder(divisor.toBigInteger())
        return listOf(Stone(firstPart), Stone(secondPart))
    }
    override  fun toString(): String{
        return number.toString()
    }
    private fun numberLength() = number.toString().length
}

val memory= mutableMapOf<Pair<BigInteger, Int>, BigInteger>()

fun blink(stone: Stone, numberOfTimes: Int): BigInteger{
    if(numberOfTimes == 0){
        return BigInteger.valueOf(1)
    }
    if(memory.containsKey(stone.number to numberOfTimes)){
        return memory[stone.number to numberOfTimes]!!
        
    }
    val result =  when {
        stone.number == BigInteger.ZERO ->  blink(Stone(BigInteger.ONE), numberOfTimes - 1)
        stone.evenNumberOfDigits() -> {
            val (f, s) = stone.split()
             blink(f, numberOfTimes-1) + blink(s, numberOfTimes-1)
        }
        else ->  blink(Stone(BigInteger.valueOf(2024) * stone.number), numberOfTimes -1)
    }
    memory[stone.number to numberOfTimes] = result
    return result
    
}
 fun String.toStones(): List<Stone> = this.split(" ").map{Stone(it.toBigInteger())}
fun main (){
    val input = "1 24596 0 740994 60 803 8918 9405859".toStones()
    val inputTest = "125 17".toStones()
    val stones = input.sumOf { blink(it, 75) }
    println(stones)
    
}