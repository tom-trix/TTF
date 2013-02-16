package ru.tomtrix.ttf

import org.eclipse.swt.widgets.{Shell, MessageBox}
import org.eclipse.swt.SWT

/**
 * gre
 */
object Wrappers {
  /**
   * sfeoiseofnj
   * @param parent fsef
   * @param msg fesfe
   */
  def showMessage(parent: Shell, msg: String) {
    val mbox = new MessageBox(parent, SWT.ICON_INFORMATION | SWT.OK)
    mbox.setMessage(msg)
    mbox.open()
  }
}
