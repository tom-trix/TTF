package ru.tomtrix.ttf

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets._
import org.eclipse.swt.graphics.Color
import ru.tomtrix.ttf.Exploit._
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.controls.ExtendedText._
import ru.tomtrix.ttf.controls.ExtendedTitle._

object Tester extends App {
  safe {
    exploitAkka("trix") { akka =>
      exploitDb(SQLITE, "ttf.sqlite") { db =>
        exploitForm("Fuck") { form =>
          exploitSplash("splash.jpg", "", 1000) { splash =>
            form.setBackground(new Color(Display.getDefault, 100, 200, 100))
            val t = new Text(form, SWT.BORDER)
            t.setBounds(30, 30, 230, 22)
            t.setTitle("Введите что-то")
            t.setSQLContent(akka, db, "SELECT city FROM Cities", None)
            println(db.getValue[Int]("SELECT COUNT(*) FROM Children"))
          }
        }
      }
    }
  }
}
