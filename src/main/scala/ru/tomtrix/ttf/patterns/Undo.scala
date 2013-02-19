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

  def doCommand(funcDo: () => Any, funcUndo: () => Any) {
    funcDo()
    undoStack push (funcDo, funcUndo)
  }

  def undo() {
    if (undoStack.size > 0) {
      val cmd = undoStack pop()
      cmd._2()
      redoStack push cmd
    }
  }

  def redo() {
    if (redoStack.size > 0) {
      val cmd = redoStack pop()
      cmd._1()
      undoStack push cmd
    }
  }
}
