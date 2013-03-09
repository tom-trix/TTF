package ru.tomtrix.ttf.controls

import ru.tomtrix.ttf.ExtendedString._
import ru.tomtrix.ttf.patterns.SafeCode._
import scala.collection.mutable.ArrayBuffer
import org.eclipse.swt.events._
import org.eclipse.swt.widgets._
import org.eclipse.swt.{SWT, widgets}
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.graphics.{Image, GC}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._
import ru.tomtrix.ttf.{SWTWrappers, Keyboard}
import ru.tomtrix.ttf.patterns.{Akka, Repository}


/**
 * fse
 */
class ExtendedText(tbox: Text) {
  private val shell = new Shell(Display getDefault, SWT.RESIZE | SWT.ON_TOP)
  private val list = new widgets.List(shell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL)
  private val btn = new Button(tbox.getShell, SWT.ARROW | SWT.DOWN | SWT.BORDER)
  private val img = new Image(Display getDefault, tbox getBounds)
  private val gc = new GC(img)
  private var data = ArrayBuffer[String]()
  private var rep: Repository = _
  private var akka: Akka = _
  private var select: String = _
  private var insert: Option[String] = _

  shell setLayout new FillLayout
  shell setSize(200, 100)
  shell addShellListener(new ShellAdapter {
    override def shellActivated(e: ShellEvent) {
      drawCaretStamp()
    }
    override def shellDeactivated(e: ShellEvent) {
      hide()
    }
  })

  btn.setSize(tbox.getSize.y, tbox.getSize.y)
  btn.setLocation(tbox.getLocation.x + tbox.getSize.x, tbox.getLocation.y)
  btn.addSelectionListener(new SelectionAdapter {
    override def widgetSelected(e: SelectionEvent) {
      list setItems data.toArray
      show()
    }
  })

  list addMouseListener(new MouseAdapter {
    override def mouseUp(e: MouseEvent) {
      accept()
    }
  })
  list addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent) {
      val pos = tbox getCaretPosition()
      e.keyCode match {
        case Keyboard.BACKSPACE => {
          tbox setSelection(pos-1, pos)
          tbox cut()
          update()
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
          update()
          e.doit = false
        }
        case SWT.ARROW_RIGHT => {
          tbox setSelection pos+1
          update()
          e.doit = false
        }
        case _ => if (e.character.toString ≉ "") {
          tbox insert e.character.toString
          update()
          e.doit = false
        }
      }
    }
  })

  tbox addModifyListener(new ModifyListener {
    def modifyText(e: ModifyEvent) {
      if (!shell.isVisible && search() > 0)
        show()
    }
  })
  tbox addFocusListener(new FocusAdapter {
    override def focusLost(e: FocusEvent) {
      val txt = tbox.getText.trim
      if (rep != null && insert!=None && (txt ≉ "") && !data.contains(txt))
        safe {
          akka.system.scheduler.scheduleOnce(500 milliseconds) {
            SWTWrappers.invokeAsynch {
              if (Display.getDefault.getActiveShell != shell && !tbox.isFocusControl) {
                println("Add new data")
                rep.execute(insert.get, Seq(txt))
                reloadData(rep.getAttribute[String](select))
              }
            }
          }
        }
    }
  })

  private def show() {
    shell setLocation(tbox.toDisplay(0, 0).x, tbox.toDisplay(0, 0).y + tbox.getSize.y + 5)
    shell open()
  }

  private def hide() {
    shell setVisible false
    tbox setBackgroundImage null
    tbox setFocus()
  }

  private def update() {
    if (search() == 0) hide()
    else drawCaretStamp()
  }

  private def accept() {
    if (list.getSelectionIndex >= 0)
      tbox setText list.getSelection.apply(0)
    tbox setSelection tbox.getText.size
    hide()
  }

  private def search() = {
    val s = tbox getText(0, tbox.getCaretPosition-1)
    list setItems data.filter{_ ⊃ s}.toArray
    list getItemCount()
  }

  private def drawCaretStamp() {
    gc fillRectangle img.getBounds
    gc drawLine(tbox.getCaretLocation.x, 0, tbox.getCaretLocation.x, tbox.getSize.y)
    tbox setBackgroundImage null
    tbox setBackgroundImage img
  }

  private def reloadData(content: Seq[String]) {
    data clear()
    data ++= content
    list setItems data.toArray
  }

  def setContent(akkaManager: Akka, content: Seq[String], comboStyle: Boolean = false) {
    akka = akkaManager
    reloadData(content)
    if (!comboStyle) btn setVisible false
  }

  def setSQLContent(akkaManager: Akka, repository: Repository, selectSQL: String, insertSQL: Option[String], comboStyle: Boolean = false) {
    rep = repository
    akka = akkaManager
    select = selectSQL
    insert = insertSQL
    reloadData(repository.getAttribute[String](selectSQL))
    if (!comboStyle) btn setVisible false
  }
}

/**
 * grgsd
 */
object ExtendedText {
  implicit def toExtendedSearch(source: Text) = new ExtendedText(source)
}
