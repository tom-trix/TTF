package ru.tomtrix.ttf

import org.eclipse.swt.widgets.{Button, Label, Shell, Display}
import org.eclipse.swt.SWT
import ExtendedShell._
import org.eclipse.swt.events.{SelectionEvent, SelectionListener, SelectionAdapter}

object Starter extends App {
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
