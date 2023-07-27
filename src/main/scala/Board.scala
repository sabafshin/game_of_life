final case class Board(cells: Array[Array[Cell]]) {
  def get(x: Int, y: Int): Cell = cells(x)(y)
  def set(x: Int, y: Int, cell: Cell): Board = Board(
    cells.updated(x, cells(x).updated(y, cell))
  )
  def width: Int = cells.length
  def height: Int = cells(0).length

  override def equals(other: Any): Boolean = other match {
    case that: Board =>
      this.cells.toList.map(_.toList) == that.cells.toList.map(_.toList)
    case _ => false
  }

  override def hashCode(): Int = cells.toList.map(_.toList).hashCode()
}
