import scala.io.StdIn.readLine

@main def runGameOfLife: Unit = {
  var board = createBoard(20, 10)
  printBoard(board)

  var generation = 0
  var prevBoard: Board = null
  var stable = false
  while (!stable) {
    Thread.sleep(350) // wait for 500 milliseconds
    val newBoard = nextGeneration(board)
    printBoard(newBoard)
    println(s"Generation: $generation")
    stable = prevBoard != null && prevBoard.equals(newBoard)
    prevBoard = board
    board = newBoard
    generation += 1
  }
  println("Game of Life has reached a stable state.")
}
