package ru.tomtrix.ttf

import ExtendedShell._
import org.eclipse.swt.SWT
import patterns.Disposable._
import org.eclipse.swt.widgets._
import org.eclipse.swt.events.{SelectionEvent, SelectionAdapter}
import patterns.{ActorsManager, Undo, Repository}
import org.apache.log4j.Logger
import SWTWrappers._
import ExtendedSearch._

object Starter extends App with Undo {
  using(ActorsManager) { g =>
    var s = "abc"
    doCommand({s = s.toUpperCase}, {s = s.toLowerCase})
    println(s)
    undo()
    println(s)
    redo()
    println(s)
    undo()
    undo()
    undo()
    undo()
    println(s)
    redo()
    println(s)

    Logger.getLogger(getClass).warn("fuck me, baby!")
    using(new Repository("trix.sqlite")(SQLITE)) { t =>
      t.getTable("Select * from Children where age < ?", Seq(14)) foreach println
      t.getTuple("Select * from Children where age < ?", Seq(14)) foreach println
      t.getAttribute[String]("Select name from Children where age < ?", Seq(14)) foreach (t => println(t))
      println("count = %d" format t.getValue[Int]("Select count(*) from Children where age < ?", Seq(14)).getOrElse(-1))
    }
    val display = new Display
    showSplashScreenFunc("images.jpg", errorText = "Loading") { t =>
      Thread.sleep(500)
    }
    val shell = new Shell(display)
    shell.setBounds(100, 100, 600, 300)
    val gt = new Text(shell, SWT.BORDER)
    gt.setLocation(30, 30)
    gt.setSize(130, 22)

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

    gt.addData(scala.List("4", "5", "6", "7"))
    shell.open()
    while (!shell.isDisposed)
      if (!display.readAndDispatch())
        display.sleep
    display.dispose()
  }
}
