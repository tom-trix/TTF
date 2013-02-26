package ru.tomtrix.ttf

import java.util.ResourceBundle
import ru.tomtrix.ttf.patterns.SafeCode._

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
    def i(args: Any*): String = safe {
      defaultBundle.bundle map {_ getString sc.s()} getOrElse sc.s()
    } getOrElse sc.s()

    def ttf(args: Any*): String = safe {
      ttfBundle.bundle map {_ getString sc.s()} getOrElse sc.s()
    } getOrElse sc.s()
  }
}

