package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val shuffled = mutableListOf<Int>()
        shuffled.addAll(1..15)
        shuffled.shuffle()
        shuffled.toMutableList()
//        println(shuffled)
        if (!isEven(shuffled)){
            val supportValue1 = shuffled[4]
            val supportValue2 = shuffled[5]
            shuffled[4]= supportValue2
            shuffled[5]= supportValue1
        }
        shuffled.toList()
    }
}

