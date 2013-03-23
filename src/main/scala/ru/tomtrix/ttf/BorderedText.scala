package ru.tomtrix.ttf

import org.eclipse.swt.SWT
import org.eclipse.swt.events._
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.widgets.{Display, Composite}

/**
 * fse
 */
class BorderedText(parent: Composite, style: Int) extends StyledText(parent, style | SWT.WRAP) {
    setTabStops(Array(0))
    addPaintListener(new PaintListener() {

      @Override
      def paintControl(e: PaintEvent ) {
        e.gc.setAntialias(SWT.ON)
        if(isFocusControl){
          e.gc.setForeground(Display.getCurrent.getSystemColor(SWT.COLOR_LIST_SELECTION))
          e.gc.drawRoundRectangle(0, 0, getClientArea.width-2, getClientArea.height-1, 6,6)
        } else {
          e.gc.setForeground(Display.getCurrent.getSystemColor(SWT.COLOR_GRAY))
          e.gc.drawRoundRectangle(0, 0, getClientArea.width-2, getClientArea.height-1, 6,6)
        }
      }
    })

    addFocusListener(new FocusListener() {

      @Override
      def focusLost(e: FocusEvent ) {
        redraw()
      }

      @Override
      def focusGained(e: FocusEvent) {
        redraw()
      }
    })

    addControlListener(new ControlListener() {

      @Override
      def controlResized(e: ControlEvent) {
        redraw()
      }

      @Override
      def controlMoved(e: ControlEvent) {
        redraw()
      }
    })

    addKeyListener(new KeyAdapter() {
      @Override
      override def keyPressed(e: KeyEvent) {
        if(e.character == SWT.TAB){
          e.doit = false
          traverse(SWT.TRAVERSE_TAB_NEXT)
        }
      }
    })
}
