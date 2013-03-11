package ru.tomtrix.ttf.controls

import ru.tomtrix.ttf.Controls._
import org.eclipse.swt.widgets.{Composite, Text}

/**
 * gre
 */
class TTFText(parent: Composite, style: Int) extends TTFControl {
  val control = new Text(parent, style)
  var width = TXT_WIDTH
  var height = TXT_HEIGHT
  var left = 0
  var top = 0

  var text = ""
  var textLimit = TEXT_LIMIT
  var passwordChar: Char = '\0'
  var message = ""

  def getContent = control getText()

  override def init() = {
    super.init()
    control setText text
    control setTextLimit textLimit
    control setMessage message
    control setEchoChar passwordChar
    this
  }

  override def setTitle(s: String) = {
    super.setTitle(s)
    this
  }
}
