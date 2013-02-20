package ru.tomtrix.ttf

import java.io._
import org.eclipse.swt.SWT
import patterns.Disposable._
import org.eclipse.swt.widgets._
import org.eclipse.swt.graphics._
import ru.tomtrix.ttf.patterns.SafeCode._

/**
 * gre
 */
object Wrappers {
  /**
   * fjoisfjoise
   * @param x fsegs
   * @return gdgrd
   */
  def serialize(x: Serializable) = {
    using(new ByteArrayOutputStream()) { baos =>
      using(new ObjectOutputStream(baos)) { oos =>
        oos writeObject x
        baos toByteArray()
      }
    }
  }

  /**
   * gjoisijgo
   * @param buf gfw
   * @return fsefas
   */
  def deserialize(buf: Array[Byte]) = {
    using(new ByteArrayInputStream(buf)) { bais =>
      using(new ObjectInputStream(bais)) { ois =>
        ois.readObject().asInstanceOf[Serializable]
      }
    }
  }
}

object SWTWrappers {
  /**
   * sfeoiseofnj
   * @param parent fsef
   * @param msg fesfe
   */
  def showMessage(parent: Shell, msg: String) {
    val mbox = new MessageBox(parent, SWT.ICON_INFORMATION | SWT.OK)
    mbox.setMessage(msg)
    mbox.open()
  }

  /**
   * sfknmselnf
   * @param filename gfsg
   * @param size grsdssef
   * @param errorText gsgs
   * @return feSAf
   */
  def showSplashScreen(filename: String, size: (Int, Int) = (480, 320), errorText: String = "") = {
    // shell properties
    val screen = Display.getDefault.getPrimaryMonitor.getBounds
    val shell = new Shell(Display getDefault, SWT.ON_TOP | SWT.APPLICATION_MODAL)
    shell setAlpha 220
    shell setSize(size._1, size._2)
    shell setLocation(screen.width/2-shell.getSize.x/2, screen.height/2-(shell.getSize.y/1.5).toInt)
    // try to load an image
    val img = safe {() => resizeImage(new Image(Display getDefault, filename), size._1, size._2)}
    shell setBackgroundImage(img.getOrElse(new Image(Display getDefault, size._1, size._2)))
    // label properties
    val gc = new GC(shell getBackgroundImage)
    gc setBackground new Color(Display getDefault, 202, 202, 222)
    if (img==None) gc fillRectangle(0, 0, size._1, size._2)
    gc setFont new Font(Display getDefault, "Arial", 26, SWT.BOLD)
    gc drawText(errorText, shell.getSize.x / 2 - gc.textExtent(errorText).x / 2, shell.getSize.y / 2 - gc.textExtent(errorText).y / 2, true)
    // open and return it
    shell open()
    shell
  }

  def showSplashScreenFunc[T](filename: String, size: (Int, Int) = (480, 320), errorText: String = "") (func: Shell => T): T = {
    using(showSplashScreen(filename, size, errorText)) {
      t => func(t)
    }
  }

  /**
   * http://aniszczyk.org/2007/08/09/resizing-images-using-swt/
   * @param image gsegs
   * @param width gthe
   * @param height rwrq
   * @return geg
   */
  def resizeImage(image: Image, width: Int, height: Int) = {
    using(image) { t =>
      val result = new Image(Display getDefault, width, height)
      using (new GC(result)) { gc =>
        gc setAntialias SWT.ON
        gc setInterpolation SWT.HIGH
        gc drawImage(t, 0, 0, t.getBounds.width, t.getBounds.height, 0, 0, width, height)
        result
      }
    }
  }
}