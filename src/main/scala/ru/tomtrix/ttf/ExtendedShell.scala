package ru.tomtrix.ttf

import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.events._
import Wrappers._

/**
 * fesfsefs
 * @param source ef
 */
class ExtendedShell(source: Shell) {
  /**
   * fsefsefs
   * @param parent fvgse
   */
  def open(parent: Shell) {
    val mouseListener = new MouseAdapter {override def mouseUp(e: MouseEvent) {showMessage(parent, "Закройте все окна")}}
    source.addDisposeListener(new DisposeListener {
      def widgetDisposed(x: DisposeEvent) {
        parent.getChildren foreach {_.setEnabled(true)}
        parent.removeMouseListener(mouseListener)
      }
    })
    parent.getChildren foreach {_.setEnabled(false)}
    parent.addMouseListener(mouseListener)
    source.open()
  }
}

/**
 * fasefas
 */
object ExtendedShell {
  implicit def toExtendedShell(s: Shell) = new ExtendedShell(s)
}
