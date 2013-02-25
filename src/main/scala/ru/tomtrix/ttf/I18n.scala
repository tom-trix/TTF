package ru.tomtrix.ttf

import ru.tomtrix.ttf.ExtendedString._
import ru.tomtrix.ttf.patterns.SafeCode._
import java.util.{Locale, ResourceBundle}

/**
 * fsv
 */
class I18nable(source: String) {
  /**
   * fjsoefjose
   * @param i18n sgrrs
   * @return gvserg
   */
  def ⇉(i18n: I18n) = i18n.bundle map {_ getString source} getOrElse source
}

/**
 * grggdp
 * @param name gdrgdr
 * @param locale htdfgr
 */
case class I18n(name: String, locale: String = "") {
  var bundle = safe {
    if (locale ≈ "") ResourceBundle getBundle name
      else ResourceBundle getBundle(name, Locale forLanguageTag locale)
  }
}

/**
 * gjogosdr
 */
object I18n {
  implicit def I18nable(s: String) = new I18nable(s)
}