package ru.tomtrix.ttf.forms

import scala.math._
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{Display, Shell}
import ru.tomtrix.ttf.I18n._
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.controls.ExtendedShell._
import ru.tomtrix.ttf.Controls._
import ru.tomtrix.ttf.controls.{TTFControl, TTFButton}

/**
 * fse
 */
object FormGenerator {
  private def rearrangeAndGetXY(elements: Seq[TTFControl]) = {
    var y = STD_MARGIN
    for {
      el <- elements
    } yield {
      el.left = STD_MARGIN
      el.top = y
      el.init()
      y += el.height + STD_MARGIN
    }
    val x = if (elements.isEmpty) STD_MARGIN else elements.map{_.width}.max + STD_MARGIN
    x -> y
  }

  def generateForm(parent: Shell)(f: (Shell) => Seq[TTFControl])(callback: Seq[Any] => Unit) {
    val shell = new Shell(Display getDefault)
    val elements = f(shell)
    val (x, y) = rearrangeAndGetXY(elements)
    new TTFButton(shell, SWT.NONE) {text=ttf"buttons.ok"; left=max(STD_MARGIN, x-STD_MARGIN-2*BTN_WIDTH); top=y; onClick = { e =>
      callback(elements.map{_.getContent})
      shell close()
    }}.init()
    new TTFButton(shell, SWT.NONE) {text=ttf"buttons.cancel"; left=max(2*STD_MARGIN+BTN_WIDTH, x-BTN_WIDTH); top=y; onClick = { e =>
      shell close()
    }}.init()
    shell pack STD_MARGIN
    putToCenter(shell)
    shell open parent
  }
}
