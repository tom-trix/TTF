package ru.tomtrix.ttf.controls

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.{Control, Group}

/**
 * vnmsdolvnsdlvs
 * @param control vgs
 */
class ExtendedTitle(control: Control) {
  /**
   * nfseonfse
   * @param s fsefse
   */
  def setTitle(s: String) {
    val gbox = new Group(control getParent, SWT.NONE)
    gbox setLayout new FillLayout()
    gbox setText s
    control setParent gbox
    gbox setLocation control.getLocation
    gbox setSize gbox.computeSize(control.getSize.x, SWT.DEFAULT, false)
  }
}

/**
 * fse
 */
object ExtendedTitle {
  /**
   * fsmeonseofsoenf
   * @param source sfegfs
   * @return gsefs
   */
  implicit def toExtendedTitle(source: Control) = new ExtendedTitle(source)
}
