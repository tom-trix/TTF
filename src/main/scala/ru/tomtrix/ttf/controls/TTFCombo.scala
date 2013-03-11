package ru.tomtrix.ttf.controls

import org.eclipse.swt.widgets.{Composite, Combo}
import ru.tomtrix.ttf.Controls._

/**
 * sef
 */
class TTFCombo(parent: Composite, style: Int) extends TTFControl {
  val control = new Combo(parent, style)
  var width: Int = TXT_WIDTH
  var height: Int = TXT_HEIGHT
  var left: Int = 0
  var top: Int = 0

  var text = ""
  var textLimit = TEXT_LIMIT

  def getContent = control getText()

  override def init() = {
    super.init()
    control setText text
    control setTextLimit textLimit
    this
  }

  override def setTitle(s: String) = {
    super.setTitle(s)
    this
  }
}
