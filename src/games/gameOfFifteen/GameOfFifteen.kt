package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen (private val initializer: GameOfFifteenInitializer): Game{
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val values = initializer.initialPermutation
        var id = 0
        for (i in 1..board.width){
            for (j in 1..board.width){
                if (id==15){
                    board.cellsMap[board.getCell(i,j)] = null
                }else {
                    board.cellsMap[board.getCell(i, j)] = values[id]
                }
                id++
            }
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        val ordenacaoAtual = board.cellsMap.values.filterNotNull()
//        println(ordenacaoAtual)
//        println(ordenacaoAtual.sortedByDescending { it }.asReversed())
        return ordenacaoAtual == ordenacaoAtual.sortedByDescending { it }.asReversed()
    }

    override fun processMove(direction: Direction) {
        val emptySpace = board.cellsMap.filterValues { it ==null }.keys.last()
        var notEmptySpace : board.Cell? = null
        board.apply { notEmptySpace = emptySpace.getNeighbour(direction.reversed()) }
        if (notEmptySpace == null) return

        board[emptySpace] = board[notEmptySpace!!]
        board[notEmptySpace!!] = null
    }

    override fun get(i: Int, j: Int): Int? {
        return board.cellsMap[board.getCell(i,j)]
    }
}
