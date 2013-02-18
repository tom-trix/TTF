package ru.tomtrix.ttf

import ExtendedShell._
import org.eclipse.swt.SWT
import patterns.Disposable._
import org.eclipse.swt.widgets.{Button, Label, Shell, Display}
import org.eclipse.swt.events.{SelectionEvent, SelectionAdapter}
import patterns.Repository
import org.apache.log4j.Logger
import ExtendedString._

object Starter extends App {
  Logger.getLogger(getClass).warn("fuck me, baby!")
  using(new Repository("trix.sqlite")(SQLITE)) { t =>
    t.getTable("Select * from Children where age < ?", Seq(14)) foreach println
    t.getTuple("Select * from Children where age < ?", Seq(14)) foreach println
    t.getAttribute[String]("Select name from Children where age < ?", Seq(14)) foreach (t => println(t))
    println("count = %d" format t.getValue[Int]("Select count(*) from Children where age < ?", Seq(14)).getOrElse(-1))
  }
  val display = new Display
  val shell = new Shell(display)

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
  lbl.pack()

  shell.pack()
  shell.open()
  while (!shell.isDisposed)
    if (!display.readAndDispatch())
      display.sleep
  display.dispose()
}
