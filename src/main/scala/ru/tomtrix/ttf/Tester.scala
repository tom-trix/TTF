package ru.tomtrix.ttf

import org.eclipse.swt.widgets._
import org.eclipse.swt.graphics.Color
import ru.tomtrix.ttf.Exploit._
import ru.tomtrix.ttf.SWTWrappers._
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.controls.TTControls._
import ru.tomtrix.ttf.controls.ExtendedTTFText._

object Tester extends App {
  safe {
    exploitAkka("trix") { akka =>
      exploitDb(SQLITE, "ttf.sqlite") { db =>
        exploitForm("Fuck") { form =>
          exploitSplash("splash.jpg", "", 1000) { splash =>
            form.setBackground(new Color(Display.getDefault, 100, 200, 100))
            println(db.getValue[Int]("SELECT COUNT(*) FROM Children"))
            createButton(new SingleParameters(form, 20, 20, "Go"){title = ""}) { e => showMessage(form, "wtf?") }
            createTextbox(new SingleParameters(form, 20, 70, ""){title = ""}){e => }._2 setContent (akka, Seq("11", "22", "33", "44"), comboStyle = true)
            createCombobox(new SingleParameters(form, 20, 120, "") {items = Seq("1", "2", "3"); title=""}) {e => }
            createCheckbox(new SingleParameters(form, 20, 170, "Fuck") {state = GRAYED; title = ""}) {e => }
            createRadiobuttons(new MultiParameters(form, 20, 220, Seq("11", "22", "33")) {title = ""}) {e => }
          }
        }
      }
    }
  }
}
