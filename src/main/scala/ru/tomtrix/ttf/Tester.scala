package ru.tomtrix.ttf

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets._
import org.eclipse.swt.graphics.Color
import ru.tomtrix.ttf.Exploit._
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.forms.FormGenerator._
import ru.tomtrix.ttf.controls.ExtendedTTFText._
import ru.tomtrix.ttf.controls._

object Tester extends App {
  safe {
    exploitAkka("trix") { akka =>
      exploitDb(SQLITE, "ttf.sqlite") { db =>
        exploitForm("Fuck") { form =>
          exploitSplash("splash.jpg", "", 1000) { splash =>
            form.setBackground(new Color(Display.getDefault, 100, 200, 100))
            new TTFText(form, SWT.BORDER) {left=30; top=30; width = 230}.setTitle("Введите что-то").setSQLContent(akka, db, "SELECT city FROM Cities", comboStyle = true)
            println(db.getValue[Int]("SELECT COUNT(*) FROM Children"))
            new TTFButton(form, SWT.NONE) {left=60; top=90; text="Go"; onClick= { e =>
              generateForm(form) { sh =>
                Seq(new TTFText(sh, SWT.BORDER) {message = "Input here"; width = 400}.init().setSQLContent(akka, db, "SELECT name FROM Children", comboStyle = true),
                    new TTFText(sh, SWT.BORDER) {message = "Input here"}.init(),
                    new TTFRadio(sh, SWT.BORDER) {items=scala.List(111, 222, 333, 444, 555); current=3}.init(),
                    new TTFButton(sh, SWT.NONE) {text = "what a hell"}.init(),
                    new TTFRadio(sh, SWT.BORDER) {items=scala.List(111, 222, 333, 444, 555); current=3}.init(),
                    new TTFCheck(sh, SWT.UP){text = "Fuck me"; state=GRAYED}.init(),
                    new TTFCombo(sh, SWT.READ_ONLY) {text = "111"; items=scala.List(111, 222, 333, 444, 555)}.init()
                )
              }{ result =>
                println(result)
              }
            }}.init()
          }
        }
      }
    }
  }
}
