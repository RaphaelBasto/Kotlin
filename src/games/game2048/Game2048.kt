package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
        Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val par = initializer.nextValue(this)
    this.cellsMap[par!!.first] = par.second
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    var boolean = false
    val newList = mutableListOf<Int?>()
    for (i in rowOrColumn){
        newList.add(this[i])
    }
    println(newList)
    val supportList = newList.moveAndMergeEqual { it.times(2) }
    println(supportList)
    for (i in rowOrColumn.indices){
        if(i<supportList.size){
            this[rowOrColumn[i]] = supportList[i]
        }
        else{
            this[rowOrColumn[i]] = null
        }
    }
    if (newList != supportList && supportList.isNotEmpty()){
        boolean = true
    }
    return boolean
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    var boolean = false
    when (direction){
        Direction.UP ->{
            for (i in 1..4){
                if (moveValuesInRowOrColumn(getColumn(1..4,i))){
                    boolean=true
                }
            }
        }
        Direction.DOWN ->{
            for (i in 1..4){
                if (moveValuesInRowOrColumn(getColumn(4 downTo 1,i))){
                    boolean=true
                }
            }
        }
        Direction.LEFT ->{
            for (i in 1..4){
                if (moveValuesInRowOrColumn(getRow(i,1..4))){
                    boolean=true
                }
            }
        }
        Direction.RIGHT ->{
            for (i in 1..4){
                if (moveValuesInRowOrColumn(getRow(i, 4 downTo 1))){
                    boolean=true
                }
            }
        }
    }
    return boolean
}