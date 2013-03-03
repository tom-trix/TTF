package ru.tomtrix.ttf

import ru.tomtrix.ttf.I18n._
import ru.tomtrix.ttf.ExtendedShell._
import org.eclipse.swt.SWT
import ru.tomtrix.ttf.patterns.Disposable._
import org.eclipse.swt.widgets._
import org.eclipse.swt.events._
import ru.tomtrix.ttf.patterns.{ActorsManager, Undo, Repository}
import org.apache.log4j.Logger
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.ExtendedText._
import org.eclipse.swt.graphics.Color

object Starter extends Application(SQLITE, "trix.sqlite", "splash.jpg", "Loading", 1000)(
  (x: Shell) => x.setBackground(new Color(Display.getDefault, 230, 120, 120))
)
    /*val display = new Display
    showSplashScreenFunc("images.jpg", text = "Loading") { t =>
      Thread.sleep(500)
    }
    val shell = new Shell(display)
    shell.setBounds(100, 100, 600, 300)
    val gt = new Text(shell, SWT.BORDER)
    gt.setLocation(30, 30)
    gt.setSize(130, 22)

    I18n.setDefaultBundle(new I18n("std_ru"))
    //showMessage(shell, i"what.a.fuck${5}${5+5}" + " " + i125"what.a.suck${131}")

    /*val f = new Text(shell, SWT.SEARCH | SWT.ICON_SEARCH)
    f.setBounds(20, 30, 120, 125)
    val img = new Image(Display.getDefault, 120, 125)
    val gc = new GC(img)
    f.addModifyListener(new ModifyListener {
      def modifyText(e: ModifyEvent) {
        gc.fillRectangle(0, 0, 120, 125)
        gc.drawLine(f.getCaretLocation.x, 0, f.getCaretLocation.x, 50)
        f.setBackgroundImage(null)
        f.setBackgroundImage(img)
      }
    })*/

    val lbl = new Button(shell, SWT.PUSH)
    lbl.setText("Hello world")
    lbl.addSelectionListener(new SelectionAdapter  {
      override def widgetSelected(e: SelectionEvent) {
        val sh = new Shell(display)
        sh.setBounds(200, 100, 200, 100)
        val lb = new Label(sh, SWT.CENTER)
        lb.setText("Fuck me")
        lb.pack()
        sh.open(shell)
      }
    })
    lbl.setBounds(100, 200, 60, 23)

    val c = new Combo(shell, SWT.DROP_DOWN)
  c.setBounds(40, 80, 160, 23)
  //c.setContent(Array("remove", "removeAll", "move", "moveAll", "rewrite", "rewriteAll"))
  //c.setContent(new Repository {val db = "ttf.sqlite"; val dbms = SQLITE}, "SELECT city FROM Cities", "INSERT INTO Cities (city) VALUES (?)")

    //gt.setContent(scala.List("remove", "removeAll", "move", "moveAll", "rewrite", "rewriteAll"), comboStyle = true)
  gt.setSQLContent(new Repository {val db = "ttf.sqlite"; val dbms = SQLITE}, "SELECT city FROM Cities", "INSERT INTO Cities (city) VALUES (?)", comboStyle = true)
    shell.open()
    while (!shell.isDisposed)
      if (!display.readAndDispatch())
        display.sleep
    display.dispose()
}*/
