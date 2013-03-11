package ru.tomtrix.ttf

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets._
import org.eclipse.swt.graphics.Color
import ru.tomtrix.ttf.Exploit._
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.forms.FormGenerator._
import ru.tomtrix.ttf.controls.ExtendedTTFText._
import ru.tomtrix.ttf.controls.{TTFButton, TTFText}

object Tester extends App {
  safe {
    exploitAkka("trix") { akka =>
      exploitDb(SQLITE, "ttf.sqlite") { db =>
        exploitForm("Fuck") { form =>
          exploitSplash("splash.jpg", "", 1000) { splash =>
            form.setBackground(new Color(Display.getDefault, 100, 200, 100))
            val t = new TTFText(form, SWT.BORDER) {left=30; top=30; width = 230}.setTitle("Введите что-то")
            t.setSQLContent(akka, db, "SELECT city FROM Cities", None)
            println(db.getValue[Int]("SELECT COUNT(*) FROM Children"))
            new TTFButton(form, SWT.NONE) {left=60; top=90; text="Go"; onClick= { e =>
              generateForm(form) { sh =>
                Seq(new TTFText(sh, SWT.BORDER) {message = "Input here"; width = 400}.setTitle("Fuck"),
                    new TTFText(sh, SWT.BORDER) {message = "Input here"}.setTitle("Suck"))
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
