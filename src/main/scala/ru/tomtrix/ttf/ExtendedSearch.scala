package ru.tomtrix.ttf

import scala.List
import collection.mutable.ArrayBuffer
import ExtendedString._
import org.eclipse.swt.events._
import org.eclipse.swt.{SWT, widgets}
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.graphics.{Image, GC}
import widgets._


/**
 * fse
 */
class ExtendedSearch(tbox: Text) {
  private val shell = new Shell(Display getDefault, SWT.RESIZE | SWT.ON_TOP)
  private val list = new widgets.List(shell, SWT.SINGLE | SWT.BORDER)
  private var data = ArrayBuffer[String]()
  val img = new Image(Display getDefault, tbox getBounds)
  val gc = new GC(img)
  val hide = () => {
    shell setVisible false
    tbox setBackgroundImage null
  }
  val accept = () => {
    tbox setText list.getSelection.apply(0)
    tbox setSelection tbox.getText.size
    hide()
  }
  val search = () => {
    val s = tbox getText(0, tbox.getCaretPosition-1)
    list removeAll()
    data filter {_ ⊃ s} foreach {list add _}
    list getItemCount()
  }
  val drawCaretStamp = () => {
    if (search() == 0) hide()
    else {
      gc fillRectangle img.getBounds
      gc drawLine(tbox.getCaretLocation.x, 0, tbox.getCaretLocation.x, tbox.getSize.y)
      tbox setBackgroundImage null
      tbox setBackgroundImage img
    }
  }

  shell setLayout new FillLayout
  shell setSize(200, 100)

  tbox addModifyListener(new ModifyListener {
    def modifyText(e: ModifyEvent) {
      if (!shell.isVisible && search() > 0) {
        shell setLocation(tbox.toDisplay(0, 0).x, tbox.toDisplay(0, 0).y + tbox.getSize.y + 5)
        shell open()
      }
    }
  })

  list addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      val pos = tbox getCaretPosition()
      e.keyCode match {
        case Keyboard.BACKSPACE => {
          tbox setSelection(pos-1, pos)
          tbox cut()
          drawCaretStamp()
        }
        case Keyboard.DELETE => {
          tbox setSelection(pos, pos+1)
          tbox cut()
        }
        case Keyboard.ENTER => accept()
        case SWT.SPACE => accept()
        case SWT.ESC => hide()
        case SWT.ARROW_LEFT => {
          tbox setSelection pos-1
          drawCaretStamp()
          e.doit = false
        }
        case SWT.ARROW_RIGHT => {
          tbox setSelection pos+1
          drawCaretStamp()
          e.doit = false
        }
        case _ => if (e.character.toString ≉ "") {
          tbox insert e.character.toString
          drawCaretStamp()
          e.doit = false
        }
      }
    }
  })

  list addMouseListener(new MouseAdapter {
    override def mouseDoubleClick(e: MouseEvent) {
      accept()
    }
  })

  shell addShellListener(new ShellAdapter {
    override def shellActivated(e: ShellEvent) {
      drawCaretStamp()
    }
    override def shellDeactivated(e: ShellEvent) {
      hide()
    }
  })

  def setSearch(searchedData: List[String]) {
    data ++= searchedData
    data foreach {list add _}
    if (data.size > 0)
      list setSelection 0
  }
}

/**
 * grgsd
 */
object ExtendedSearch {
  implicit def toExtendedSearch(source: Text) =
    new ExtendedSearch(source)
}
