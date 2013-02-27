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

  val format = (sc: StringContext, i18n: I18n, args: Seq[AnyRef]) => safe {
    val s = sc.parts.foldLeft("") {(a, b) => s"$a$b"}
    val z = for {value <- i18n.bundle map {_ getString s}}
            yield MessageFormat.format(value, args: _*)
    z getOrElse "error"
  } getOrElse "error"

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
    def i(args: Any*): String = format(sc, defaultBundle, args map {_ toString()})

    def ttf(args: Any*): String = format(sc, ttfBundle, args map {_ toString()})
  }
}

