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
      if (e.keyCode == SWT.SPACE || e.keyCode == 13)
        accept()
      else if (e.keyCode == SWT.ESC)
        hide()
      else if (!e.character.toString.trim.isEmpty)
        //tbox.setText(tbox.getText.substring(0, tbox.getCaretPosition) + e.character + tbox.getText.substring(tbox.getCaretPosition+1))
      {
        sourceShell.setActive()
        val g = tbox.getText splitAt tbox.getCaretPosition-1
        tbox.insert(e.character.toString)
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
      gc fillRectangle img.getBounds
      gc drawLine(tbox.getCaretLocation.x, 0, tbox.getCaretLocation.x, tbox.getSize.y)
      tbox setBackgroundImage img
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
