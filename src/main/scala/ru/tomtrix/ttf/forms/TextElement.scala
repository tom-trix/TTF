package ru.tomtrix.ttf.forms

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{Text, Shell}
import ru.tomtrix.ttf.Controls._

/**
 * fes
 */
class TextElement(shell: Shell, val title: String, val default: String = "", inviteText: String = "") extends Element {
  val txtbox = new Text(shell, SWT.BORDER)
  txtbox setMessage inviteText
  txtbox setText default
  txtbox setBounds(20, 20, TXT_WIDTH, TXT_HEIGHT)
  val control = txtbox //setTitle title

  def getResult = txtbox getText()
}
