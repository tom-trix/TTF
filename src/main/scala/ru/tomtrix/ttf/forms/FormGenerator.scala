package ru.tomtrix.ttf.forms

import scala.math._
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{Display, Shell}
import ru.tomtrix.ttf.I18n._
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.controls.ExtendedShell._
import ru.tomtrix.ttf.Controls._

/**
 * fse
 */
object FormGenerator {
  val MARGIN = 10

  private def rearrangeAndGetXY(elements: Seq[Element]) = {
    var y = MARGIN
    for {
      el <- elements
    } yield {
      el.control.setLocation(MARGIN, y)
      y += el.control.getSize.y + MARGIN
    }
    elements.map{_.control.getSize.x}.max+MARGIN -> y
  }

  def generateForm(parent: Shell)(f: (Shell) => Seq[Element])(callback: Seq[Any] => Unit) {
    val shell = new Shell(Display getDefault)
    val elements = f(shell)
    val (x, y) = rearrangeAndGetXY(elements)
    createButton(shell, SWT.NONE, ttf"buttons.ok", max(MARGIN, x-MARGIN-2*BTN_WIDTH), y) {
      callback(elements.map{_.getResult})
      shell close()
    }
    createButton(shell, SWT.NONE, ttf"buttons.cancel", max(2*MARGIN+BTN_WIDTH, x-BTN_WIDTH), y) {
      shell close()
    }
    shell pack MARGIN
    putToCenter(shell)
    shell open parent
  }
}
