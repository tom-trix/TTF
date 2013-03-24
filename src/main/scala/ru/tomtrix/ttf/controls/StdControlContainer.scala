package ru.tomtrix.ttf.controls

import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite

/**
 * htr
 */
object StdControlContainer {
  type Textable = {
    def setText(s: String)
    def setBounds(x: Int, y: Int, width: Int, height: Int)
    //@Suppress(Method with accessor-like name is empty-paren)
    def getSize(): Point
    //@Suppress(Method with accessor-like name is empty-paren)
    def getLocation(): Point
  }
}
