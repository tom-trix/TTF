package ru.tomtrix.ttf.controls

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{Composite, Button}
import org.eclipse.swt.events.{SelectionAdapter, SelectionEvent}
import ru.tomtrix.ttf.Controls._
import ru.tomtrix.ttf.{CHECKSTATE, GRAYED, UNCHECKED, CHECKED}

/**
 * fse
 */
class TTFCheck(parent: Composite, style: Int) extends TTFControl {
  val control = new Button(parent, style | SWT.CHECK)
  var width: Int = CHECK_WIDTH
  var height: Int = CHECK_HEIGHT
  var left: Int = 0
  var top: Int = 0

  var text = ""
  var state: CHECKSTATE = UNCHECKED
  var onChange: (SelectionEvent) => Unit = (e: SelectionEvent) => {}

  def getContent =
    if (control.getGrayed && control.getSelection) GRAYED
    else if (control.getSelection) CHECKED
    else UNCHECKED

  override def init() = {
    super.init()
    control setText text
    state match {
      case CHECKED => control setSelection true
      case UNCHECKED => control setSelection false
      case GRAYED => control setGrayed true; control setSelection true
      case _ =>
    }
    control addSelectionListener new SelectionAdapter {
      override def widgetSelected(e: SelectionEvent) {
        control setGrayed false
        onChange(e)
      }
    }
    this
  }

  override def setTitle(s: String) = {
    super.setTitle(s)
    this
  }
}
