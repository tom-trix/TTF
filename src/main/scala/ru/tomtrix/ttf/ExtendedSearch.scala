package ru.tomtrix.ttf

import SWTWrappers._
import akka.actor.Actor
import patterns.ActorsManager
import org.eclipse.swt.events._
import org.eclipse.swt.{SWT, widgets}
import org.eclipse.swt.layout.FillLayout
import widgets._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._
import collection.mutable.ArrayBuffer
import scala.List
import org.eclipse.swt.graphics.{Image, Color, GC}


/**
 * fse
 */
class ExtendedSearch(tbox: Text) {
  private val shell = new Shell(Display getDefault, SWT.RESIZE | SWT.ON_TOP)
  private val list = new widgets.List(shell, SWT.SINGLE | SWT.BORDER)
  private val sourceShell = tbox getShell()
  private val data = ArrayBuffer[String]()
  val img = new Image(Display getDefault, tbox.getBounds)
  val gc = new GC(img)
  val hide = () => shell setVisible false
  val accept = () => {
    tbox.setText(list.getSelection.apply(0))
    hide()
  }
  val moveSelection = (t: Int) => {
    shell setActive()
    val i = list.getSelectionIndex+t
    if (0 <= i && i < list.getItemCount)
      list setSelection i
  }
  val drawCaretStamp = () => {
    gc fillRectangle img.getBounds
    gc drawLine(tbox.getCaretLocation.x, 0, tbox.getCaretLocation.x, tbox.getSize.y)
    tbox setBackgroundImage null
    tbox setBackgroundImage img
  }
  shell.setLayout(new FillLayout())
  shell setSize(200, 100)

  tbox.addModifyListener(new ModifyListener {
    def modifyText(e: ModifyEvent) {
      shell setLocation(tbox.toDisplay(0,0).x, tbox.toDisplay(0,0).y + tbox.getSize.y + 5)
      if (!shell.isVisible) {
        shell.open()
        sourceShell.setActive()
      }
    }
  })

  tbox.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      if (shell.isVisible)
        e.keyCode match {
          case SWT.ARROW_DOWN => {
            moveSelection(1)
            e.doit = false
          }
          case SWT.ARROW_UP => {
            moveSelection(-1)
            e.doit = false
          }
          case SWT.ESC => hide()
          case _ =>
        }
    }
  })

  list.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      e.keyCode match {
        case SWT.SPACE => accept()
        case 13 => accept()
        case SWT.ESC => hide()
        case SWT.ARROW_LEFT => {
          tbox.setSelection(tbox.getCaretPosition-1)
          drawCaretStamp()
          e.doit = false
        }
        case SWT.ARROW_RIGHT => {
          tbox.setSelection(tbox.getCaretPosition+1)
          drawCaretStamp()
          e.doit = false
        }
        case _ => if (!e.character.toString.trim.isEmpty) {
          tbox insert(e.character.toString)
          drawCaretStamp()
        }
      }
    }
  })

  list.addMouseListener(new MouseAdapter {
    override def mouseDoubleClick(e: MouseEvent) {
      accept()
    }
  })

  sourceShell.addShellListener(new ShellAdapter {
    override def shellIconified(e: ShellEvent) {
      hide()
    }
  })

  (sourceShell :: sourceShell.getChildren.toList) foreach {t => t.addMouseListener(new MouseAdapter {
    override def mouseDown(e: MouseEvent) {
      hide()
    }
  })}

  shell.addShellListener(new ShellAdapter {
    override def shellActivated(e: ShellEvent) {
      drawCaretStamp()
    }
    override def shellDeactivated(e: ShellEvent) {
      tbox setBackgroundImage null
    }
  })

  ActorsManager.system.scheduler.schedule(0 minutes, 127 milliseconds) {
    invokeAsynch {
      val curShell = Display.getDefault.getActiveShell
      if (curShell != shell && curShell != sourceShell && !shell.isDisposed)
        hide()
    }
  }

  def addData(searchedData: List[String]) {
    data ++= searchedData
    data foreach {list add _}
    if (data.size > 0)
      list setSelection 0
  }
}

object ExtendedSearch {
  implicit def toExtendedSearch(source: Text) =
    new ExtendedSearch(source)
}
