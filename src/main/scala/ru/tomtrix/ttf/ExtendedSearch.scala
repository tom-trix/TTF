package ru.tomtrix.ttf

import SWTWrappers._
import akka.actor.Actor
import patterns.ActorsManager
import org.eclipse.swt.events._
import org.eclipse.swt.{SWT, widgets}
import org.eclipse.swt.layout.FillLayout
import widgets.{Display, Shell, Text}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._


/**
 * fse
 */
class ExtendedSearch(tbox: Text) {
  private val shell = new Shell(Display getDefault, SWT.RESIZE | SWT.ON_TOP)
  private val list = new widgets.List(shell, SWT.SINGLE | SWT.BORDER)
  private val sourceShell = tbox getShell()
  val accept = () => {
    tbox.setText(list.getSelection.apply(0))
    shell setVisible false
  }
  val moveSelection = (t: Int) => {
    shell.setActive()
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
      e.keyCode match {
        case SWT.ARROW_DOWN => moveSelection(1)
        case SWT.ARROW_UP => moveSelection(-1)
        case SWT.ESC => shell setVisible false
        case _ =>
      }
    }
  })

  list.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      if (e.keyCode == SWT.SPACE || e.keyCode == 13)
        accept()
      else if (e.keyCode == SWT.ESC)
        shell setVisible false
    }
  })

  list.addMouseListener(new MouseAdapter {
    override def mouseDoubleClick(e: MouseEvent) {
      accept()
    }
  })

  sourceShell.addShellListener(new ShellAdapter {
    override def shellIconified(e: ShellEvent) {
      shell setVisible false
    }
  })

  (sourceShell :: sourceShell.getChildren.toList) foreach {t => t.addMouseListener(new MouseAdapter {
    override def mouseDown(e: MouseEvent) {
      shell setVisible false
    }
  })}

  ActorsManager.system.scheduler.schedule(0.minutes, 127.milliseconds) {
    invokeAsynch {
      val curShell = Display.getDefault.getActiveShell
      if (curShell != shell && curShell != sourceShell && !shell.isDisposed)
        shell setVisible false
    }
  }

  def addData(data: List[String]) {
    data foreach {list add _}
    if (data.size > 0)
      list.setSelection(0)
  }
}

class TrixActor extends Actor {
  def receive = {
    case "trix" => println(444)
  }
}

object ExtendedSearch {
  implicit def toExtendedSearch(source: Text) =
    new ExtendedSearch(source)
}
