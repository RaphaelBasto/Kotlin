package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = Board(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = Board2(width)

