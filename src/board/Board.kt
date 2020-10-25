package board

import java.lang.IllegalArgumentException
import java.util.*

data class Cell(val i: Int, val j: Int) {
    override fun toString()= "($i, $j)"
}

enum class Direction {
    UP, DOWN, RIGHT, LEFT;

    fun reversed() = when (this) {
        UP -> DOWN
        DOWN -> UP
        RIGHT -> LEFT
        LEFT -> RIGHT
    }
}

interface SquareBoard {
    val width: Int
    val cells: Collection<Cell>

    fun getCellOrNull(i: Int, j: Int): Cell?{
        if (i !in 1..this.width || j !in 1..width){
            return null
        }
        return getCell(i,j)
    }
    fun getCell(i: Int, j: Int): Cell{
        if (i !in 1..this.width || j !in 1..width){
            throw IllegalArgumentException()
        }
        var id = 0
        if (this.cells.contains(Cell(i,j))){
            id = this.cells.indexOf(Cell(i,j))
        }
        return this.cells.elementAt(id)
    }

    fun getAllCells(): Collection<Cell>{
        return this.cells
    }

    fun getRow(i: Int, jRange: IntProgression): List<Cell>{
        val givenRow: MutableList<Cell> = mutableListOf()
        for (j in jRange){
            if (this.cells.contains(Cell(i,j))){
                givenRow.add(getCell(i, j))
            }
        }
        return givenRow
    }
    fun getColumn(iRange: IntProgression, j: Int): List<Cell>{
        val givenColumn: MutableList<Cell> = mutableListOf()
        for (i in iRange){
            if (this.cells.contains(Cell(i,j))){
                givenColumn.add(getCell(i, j))
            }
        }
        return givenColumn
    }

    fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.UP -> getCellOrNull(i-1,j)
            Direction.DOWN -> getCellOrNull(i+1,j)
            Direction.LEFT -> getCellOrNull(i,j-1)
            Direction.RIGHT -> getCellOrNull(i,j+1)
        }
    }
}

interface GameBoard<T> : SquareBoard {
    val cellsMap: MutableMap<Cell, T?>

    operator fun get(cell: Cell): T?{
        return cellsMap[getCellOrNull(cell.i,cell.j)]
    }
    operator fun set(cell: Cell, value: T?){
        cellsMap[cell] = value
        println("O novo valor de $cell é $value")
    }

    fun filter(predicate: (T?) -> Boolean): Collection<Cell>{
        val supSet = cellsMap.filter { predicate.invoke(it.value) }.keys
        val finalSet = mutableSetOf<Cell>()
        for (k in supSet){
            finalSet.add(getCell(k.i,k.j))
        }
        return finalSet
    }
    fun find(predicate: (T?) -> Boolean): Cell?{
        return getCellOrNull(cellsMap.filter { predicate.invoke(it.value) }.keys.first().i,
                cellsMap.filter { predicate.invoke(it.value) }.keys.first().j)
    }
    fun any(predicate: (T?) -> Boolean): Boolean{
        return cellsMap.any { predicate.invoke(it.value) }
    }
    fun all(predicate: (T?) -> Boolean): Boolean{
        return cellsMap.all { predicate.invoke(it.value) }
    }
}

class Board(override val width: Int): SquareBoard{
    private val allCells: MutableSet<Cell> = mutableSetOf()
    init {
        for (i in 1..width){
            for (j in 1..width){
                allCells.add(Cell(i,j))
            }
        }
    }

    override val cells: MutableSet<Cell>
        get() = allCells
}
class Board2<T> (override val width: Int): GameBoard<T>{
    private val mapOfCells: MutableMap<Cell,T?> = mutableMapOf()
    private val allCells: MutableSet<Cell> = mutableSetOf()
    init {
        for (i in 1..width){
            for (j in 1..width){
                mapOfCells[Cell(i,j)] = null
                allCells.add(Cell(i,j))
            }
        }
        println(mapOfCells)
    }

    override val cellsMap: MutableMap<Cell, T?>
        get() = mapOfCells

    override val cells: Collection<Cell>
        get() = allCells
}
