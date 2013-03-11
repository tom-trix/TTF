package ru.tomtrix.ttf.controls

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{Composite, Button}
import org.eclipse.swt.events.{SelectionAdapter, SelectionEvent}
import ru.tomtrix.ttf.{CHECKSTATE, GRAYED, UNCHECKED, CHECKED}

/**
 * fse
 */
class TTFCheck(parent: Composite, style: Int) extends TTFControl {
  val control = new Button(parent, style | SWT.CHECK)
  var width: Int = _
  var height: Int = _
  var left: Int = 0
  var top: Int = 0

  var text = ""
  var state: CHECKSTATE = UNCHECKED
  var onChange: (SelectionEvent) => Unit = _

  def getContent =
    if (control.getGrayed) GRAYED
    else if (control.getSelection) CHECKED
    else UNCHECKED

  override def init() = {
    super.init()
    control setText text
    state match {
      case CHECKED => control setSelection true
      case UNCHECKED => control setSelection false
      case GRAYED => control setGrayed true
      case _ =>
    }
    control addSelectionListener new SelectionAdapter {
      override def widgetSelected(e: SelectionEvent) {onChange(e)}
    }
    this
  }

  override def setTitle(s: String) = {
    super.setTitle(s)
    this
  }
}
