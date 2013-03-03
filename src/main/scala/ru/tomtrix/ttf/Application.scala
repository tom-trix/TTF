package ru.tomtrix.ttf

import ru.tomtrix.ttf.patterns.Disposable._
import org.eclipse.swt.widgets.{Shell, Display}
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.patterns.{Repository, ActorsManager}
import ru.tomtrix.ttf.SWTWrappers._

/**
 * fsefse
 */
class Application(dbmsname: DBMS, dbname: String, splashname: String, splashtext: String, splashtimeMillis: Int)(f: Shell => Unit) extends App {
  safe {
    using(new Display()) { display =>
      using(ActorsManager) { akka =>
        using(new Repository {val db: String = dbname; val dbms: DBMS = dbmsname}) { db =>
          val mainForm = new Shell(display)
          showSplashScreenFuncTime(splashname, text = splashtext, millisec = splashtimeMillis) { splash =>
            mainForm.setSize(500, 350)
            f(mainForm)
            mainForm.setLocation(display.getBounds.width/2 - mainForm.getSize.x/2, display.getBounds.height/2 - mainForm.getSize.y/2 - 20)
          }
          mainForm open()
          while (!mainForm.isDisposed)
            if (!display.readAndDispatch()) display sleep()
        }
      }
    }
  }
}
