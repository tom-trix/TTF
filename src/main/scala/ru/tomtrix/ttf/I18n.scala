package ru.tomtrix.ttf

import ru.tomtrix.ttf.patterns.SafeCode._
import java.text.MessageFormat
import java.util.ResourceBundle

/**
 * grggdp
 * @param name gdrgdr
 */
case class I18n(name: String) {
  var bundle = safe {
    ResourceBundle getBundle name
  }
}

/**
 * gjogosdr
 */
object I18n {
  private var defaultBundle: I18n = null
  private var ttfBundle = new I18n("ttf_ru")

  val format = (s: String, i18n: I18n, args: Seq[AnyRef]) => safe {
    val z = for {value <- i18n.bundle map {_ getString s}}
            yield MessageFormat.format(value, args: _*)
    z getOrElse "error"
  } getOrElse "error"

  private def fuse(sc: StringContext) = sc.parts.foldLeft("") {(a, b) => s"$a$b"}

  def setDefaultBundle(x: I18n) {
    defaultBundle = x
  }

  def setTtfBundle(x: I18n) {
    ttfBundle = x
  }

  /**
   * fseojseoffe
   * @param sc fse
   */
  implicit class I18nInterpoler(val sc: StringContext) extends AnyVal {
    def i(args: Any*): String = format(fuse(sc), defaultBundle, args map {_ toString()})

    def i125(args: Any*): String = {
      if (args.size == 1 && args(0).isInstanceOf[Int]) {
        val ends = (t: Int) => args(0).toString endsWith t.toString
        val postfix = if ((11 to 14) exists ends) "5"
                  else if (ends(1)) "1"
                  else if ((2 to 4) exists ends) "2"
                  else "5"
        format(s"${fuse(sc)}$postfix", defaultBundle, args map {_ toString()})
      }
      else format(fuse(sc), defaultBundle, args map {_ toString()})
    }

    def ttf(args: Any*): String = format(fuse(sc), ttfBundle, args map {_ toString()})
  }
}

