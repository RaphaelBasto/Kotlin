package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    var int = 0
    val newList = permutation.toMutableList()
    for (i in permutation.sortedDescending().asReversed()){
        val id = newList.indexOf(i)
        if (permutation.contains(0)){
            if (id != i){
                val supportValue = newList[i]
                newList[i]=i
                newList[id] = supportValue
                int++
            }
        }else{
            if (id != i-1){
                val supportValue = newList[i-1]
                newList[i-1]=i
                newList[id] = supportValue
                int++
            }
        }
    }
    return int%2 ==0
}