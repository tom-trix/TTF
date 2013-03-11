package ru.tomtrix.ttf.controls

import org.eclipse.swt.widgets.{Button, Composite}
import org.eclipse.swt.SWT
import ru.tomtrix.ttf.Controls._

/**
 * fse
 */
class TTFRadio(parent: Composite, style: Int) extends TTFControl {
  val control = new Composite(parent, style)
  var width: Int = RADIOGROUP_WIDTH
  var height: Int = RADIOGROUP_HEIGHT
  var left: Int = 0
  var top: Int = 0

  var current = -1
  var margin = STD_MARGIN
  var items: Seq[Any] = Nil

  def getContent = control .getChildren.zipWithIndex.find {
    t => t._1.asInstanceOf[Button].getSelection
  }.map {_._2} getOrElse -1

  override def init() = {
    super.init()
    var y = margin
    items.zipWithIndex foreach { i =>
      val b = new Button(control, SWT.RADIO)
      b setText i._1.toString
      b setBounds (margin, y, RADIO_WIDTH, RADIO_HEIGHT)
      y += b.getSize.y + margin
      if (i._2==current) b setSelection true
    }
    control.setSize(control.getChildren.map{_.getSize.x}.max + margin, y)
    width = control.getSize.x
    height = control.getSize.y
    left = control.getLocation.x
    top = control.getLocation.y
    this
  }

  override def setTitle(s: String) = {
    super.setTitle(s)
    this
  }
}
