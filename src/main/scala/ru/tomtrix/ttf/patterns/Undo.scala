package ru.tomtrix.ttf.patterns

import java.util
import collection.mutable

/**
 * egsge
 */
trait Undo extends mutable.Undoable {
  type Cmd = (() => Any, () => Any)

  val undoStack = new util.ArrayDeque[Cmd]()
  val redoStack = new util.ArrayDeque[Cmd]()
  var size = 100

  private def push(cmd: Cmd) {
    undoStack push cmd
    if (undoStack.size > size)
      undoStack pollLast()
  }

  /**
   * nsfeonfose
   * @param funcDo fgses
   * @param funcUndo hrtrht
   */
  def doCommand(funcDo: => Any, funcUndo: => Any) {
    funcDo
    push (() => funcDo, () => funcUndo)
  }

  /**
   * fnksefs
   */
  def undo() {
    if (undoStack.size > 0) {
      val cmd = undoStack pop()
      cmd._2()
      redoStack push cmd
    }
  }

  /**
   * nfksbda
   */
  def redo() {
    if (redoStack.size > 0) {
      val cmd = redoStack pop()
      cmd._1()
      push(cmd)
    }
  }
}
