import com.madgag.gif.fmsware.AnimatedGifEncoder

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

@main def runGameOfLife: Unit = {
  var board = createBoard(20, 10)
  printBoard(board)

  var generation = 0
  var prevBoard: Board = null
  var stable = false

  val outputDir = "output"
  val outputFile = new File(outputDir, "game_of_life.gif")

  if (!outputFile.getParentFile.exists()) {
    outputFile.getParentFile.mkdirs()
  }

  if (outputFile.exists()) {
    outputFile.delete()
  }

  val encoder = new AnimatedGifEncoder()
  encoder.start(outputFile.getAbsolutePath)
  encoder.setDelay(350) // set delay between frames to 350 milliseconds
  encoder.setRepeat(0) // set repeat count to 0 (infinite)

  while (!stable) {
    Thread.sleep(350) // wait for 350 milliseconds
    val newBoard = nextGeneration(board)
    printBoard(newBoard)
    generation += 1
    println(s"Generation: $generation")
    stable = prevBoard != null && prevBoard.equals(newBoard)
    prevBoard = board
    board = newBoard

    // Capture the current frame and add it to the GIF
    val image = new BufferedImage(
      board.width * 10,
      board.height * 10,
      BufferedImage.TYPE_INT_RGB
    )
    val graphics = image.createGraphics()
    graphics.setBackground(Color.WHITE)
    graphics.clearRect(0, 0, image.getWidth, image.getHeight)
    printBoardToGraphics(newBoard, graphics, 10, 0, 0)
    encoder.addFrame(image)
  }

  encoder.finish()

  println(
    s"Game of Life has reached a stable state. GIF saved to ${outputFile.getAbsolutePath}"
  )
}
