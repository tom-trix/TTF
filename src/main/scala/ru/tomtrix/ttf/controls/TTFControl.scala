package ru.tomtrix.ttf.controls

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.{Group, Control}

/**
 * few
 */
trait TTFControl {
  val control: Control
  var width: Int
  var height: Int
  var left: Int
  var top: Int
  var geometry: Control = null

  def getContent: Any

  def init() = {
    if (geometry == null) geometry = control
    geometry setBounds(left, top, width, height)
    this
  }

  def setTitle(s: String) = {
    init()
    val gbox = new Group(control getParent, SWT.NONE)
    gbox setText s
    gbox setLayout new FillLayout()
    control setParent gbox
    gbox setLocation control.getLocation
    gbox setSize gbox.computeSize(control.getSize.x, SWT.DEFAULT, false)
    width = gbox.getSize.x
    height = gbox.getSize.y
    left = gbox.getLocation.x
    top = gbox.getLocation.y
    geometry = gbox
    this
  }
}
