package ru.tomtrix.ttf.forms

import scala.math._
import org.eclipse.swt.widgets.{Composite, Display, Shell}
import ru.tomtrix.ttf.I18n._
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.controls.ExtendedShell._
import ru.tomtrix.ttf.Controls._
import ru.tomtrix.ttf.controls.TTFControl._
import ru.tomtrix.ttf.controls.{TTFControl, SingleParameters}
import ru.tomtrix.ttf.controls.StdControlContainer._

/**
 * fse
 */
object FormGenerator {
private def rearrange(elements: Seq[Composite]) {
  val curX = STD_MARGIN
  var curY = STD_MARGIN
  for {
    element <- elements
  } yield {
    element setLocation(curX, curY)
    curY += STD_MARGIN + element.getSize.y
  }
}

def generateForm(parent: Shell, ctrls: Shell => Seq[TTFControl[Any]]) (f: Seq[String] => Unit) {
  val shell = new Shell(Display getDefault)
  val controls = ctrls(shell)
  val elements = controls map {_.container}
  rearrange(elements)
  val maxX = elements maxBy {c => c.getLocation.x + c.getSize.x}
  val maxY = elements maxBy {c => c.getLocation.y + c.getSize.y}
  val (x, y) = (maxX.getLocation.x + maxX.getSize.x, maxY.getLocation.y + maxY.getSize.y)
  createButton(new SingleParameters(shell, max(STD_MARGIN, x-STD_MARGIN-2*STD_WIDTH), y, ttf"buttons.ok")) { e =>
    f(controls map {_.getResult})
    shell close()
  }
  createButton(new SingleParameters(shell, max(2*STD_MARGIN+STD_WIDTH,  x-STD_WIDTH), y, ttf"buttons.cancel")) { e =>
    shell close()
  }
  shell pack STD_MARGIN
  putToCenter(shell)
  shell open parent
  }
}
