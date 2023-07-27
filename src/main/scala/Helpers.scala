import scala.util.Random

def createBoard(width: Int, height: Int): Board = {
  val cells = Array.ofDim[Cell](width, height)
  for (x <- 0 until width) {
    for (y <- 0 until height) {
      val alive = Random.nextBoolean()
      cells(x)(y) = Cell(alive)
    }
  }
  Board(cells)
}
def nextGeneration(board: Board): Board = {
  val newCells = Array.ofDim[Cell](board.width, board.height)
  for (x <- 0 until board.width) {
    for (y <- 0 until board.height) {
      val cell = board.get(x, y)
      val neighbors = getNeighbors(board, x, y)
      val aliveNeighbors = neighbors.filter(_.alive).length
      val newCell = if (cell.alive) {
        if (aliveNeighbors < 2 || aliveNeighbors > 3) Cell(false)
        else Cell(true)
      } else {
        if (aliveNeighbors == 3) Cell(true) else Cell(false)
      }
      newCells(x)(y) = newCell
    }
  }
  Board(newCells)
}

def getNeighbors(board: Board, x: Int, y: Int): List[Cell] = {
  val indices = List(-1, 0, 1)
  val neighbors = for {
    i <- indices
    j <- indices
    if !(i == 0 && j == 0)
    newX = x + i
    newY = y + j
    if newX >= 0 && newX < board.width && newY >= 0 && newY < board.height
  } yield board.get(newX, newY)
  neighbors
}

def getDiagonalNeighbors(board: Board, x: Int, y: Int): List[Cell] = {
  val indices = List(-1, 0, 1)
  val neighbors = for {
    i <- indices
    j <- indices
    if !(i == 0 && j == 0) && (Math.abs(i + j) == 1)
    newX = x + i
    newY = y + j
    if newX >= 0 && newX < board.width && newY >= 0 && newY < board.height
  } yield board.get(newX, newY)
  neighbors
}

def printBoard(board: Board): Unit = {
  print("\u001b[2J") // clear the console
  print("\u001b[H") // move the cursor to the top-left corner
  print("┌" + "─" * (board.width * 2 - 1) + "┐\n")
  for (y <- 0 until board.height) {
    print("│")
    for (x <- 0 until board.width) {
      val cell = board.get(x, y)
      val isAlive = cell.alive
      val isAboutToDie =
        !isAlive && (getNeighbors(board, x, y) ++ getDiagonalNeighbors(
          board,
          x,
          y
        )).count(_.alive) == 2
      if (isAlive) {
        print("\u001b[32m●\u001b[0m") // green circle for alive cells
      } else if (isAboutToDie) {
        print(
          "\u001b[31m●\u001b[0m"
        ) // red circle for cells that are about to die
      } else {
        print(" ")
      }
      if (x < board.width - 1) {
        print(" ")
      }
    }
    print("│\n")
    if (y < board.height - 1) {
      print("│" + " " * (board.width * 2 - 1) + "│\n")
    }
  }
  print("└" + "─" * (board.width * 2 - 1) + "┘\n")
}
