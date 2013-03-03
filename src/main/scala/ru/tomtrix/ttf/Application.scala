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
            val monitorSize = display.getPrimaryMonitor.getBounds
            mainForm.setSize(500, 350)
            f(mainForm)
            mainForm.setLocation(monitorSize.width/2 - mainForm.getSize.x/2, monitorSize.height/2 - mainForm.getSize.y/2 - 20)
          }
          mainForm open()
          while (!mainForm.isDisposed)
            if (!display.readAndDispatch()) display sleep()
        }
      }
    }
  }
}
