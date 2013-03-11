package ru.tomtrix.ttf.controls

import ru.tomtrix.ttf.Controls._
import org.eclipse.swt.widgets.{Button, Composite}
import org.eclipse.swt.events.{SelectionAdapter, SelectionEvent}

/**
 * rge
 */
class TTFButton(parent: Composite, style: Int) extends TTFControl {
  val control = new Button(parent, style)
  var width: Int = BTN_WIDTH
  var height: Int = BTN_HEIGHT
  var left: Int = 0
  var top: Int = 0

  var text = ""
  var onClick: (SelectionEvent) => Unit = _

  def getContent = control getSelection()

  override def init() = {
    super.init()
    control setText text
    control addSelectionListener(new SelectionAdapter {
      override def widgetSelected(e: SelectionEvent) {onClick(e)}
    })
    this
  }

  override def setTitle(s: String) = {
    super.setTitle(s)
    this
  }
}
