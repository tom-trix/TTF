package ru.tomtrix.ttf

import org.eclipse.swt.widgets._
import org.eclipse.swt.graphics.Color
import ru.tomtrix.ttf.Exploit._
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.controls.TTFControl._
import ru.tomtrix.ttf.controls.{MultiParameters, SingleParameters}
import ru.tomtrix.ttf.forms.FormGenerator._

object Tester extends App {
  safe {
    exploitAkka("trix") { akka =>
      exploitDb(SQLITE, "ttf.sqlite") { db =>
        exploitForm("Fuck") { form =>
          exploitSplash("splash.jpg", "", 1000) { splash =>
            form.setBackground(new Color(Display.getDefault, 100, 200, 100))
            println(db.getValue[Int]("SELECT COUNT(*) FROM Children"))
            createButton(new SingleParameters(form, 20, 20, "Go"){title = "1"}) { e =>
              generateForm(form, sh => Seq (
                createTextbox(new SingleParameters(sh, 0, 0, "")) {e => },
                createCombobox(new SingleParameters(sh, 0, 0, "")) {e => }
                )) { ew => }
            }
            createTextbox(new SingleParameters(form, 20, 70, ""){title = "2"}){e => }
            createCombobox(new SingleParameters(form, 20, 120, "") {items = Seq("1", "2", "3"); title="3"}) {e => }
            createCheckbox(new SingleParameters(form, 20, 170, "Fuck") {state = GRAYED; title = "4"}) {e => }
            createRadiobuttons(new MultiParameters(form, 20, 220, Seq("11", "22", "33")) {title = "5"}) {e => }
          }
        }
      }
    }
  }
}
