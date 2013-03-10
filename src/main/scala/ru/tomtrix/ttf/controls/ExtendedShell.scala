package ru.tomtrix.ttf.controls

import org.eclipse.swt.events._
import org.eclipse.swt.widgets.Shell
import ru.tomtrix.ttf.I18n._
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.patterns.SafeCode._

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
    val mouseListener = new MouseAdapter {override def mouseUp(e: MouseEvent) {
      showMessage(parent, ttf"close.all.windows")
      source setActive()
    }}
    source addDisposeListener new DisposeListener {
      def widgetDisposed(x: DisposeEvent) {
        safe {
          parent.getChildren foreach {_.setEnabled(true)}
          parent removeMouseListener mouseListener
        }
      }
    }
    parent.getChildren foreach {_.setEnabled(false)}
    parent addMouseListener mouseListener
    source open()
  }

  def pack(margin: Int) {
    source pack()
    source setSize(source.getSize.x + margin, source.getSize.y +margin)
    source setMinimumSize source.getSize
  }
}

/**
 * fasefas
 */
object ExtendedShell {
  implicit def toExtendedShell(s: Shell) = new ExtendedShell(s)
}
