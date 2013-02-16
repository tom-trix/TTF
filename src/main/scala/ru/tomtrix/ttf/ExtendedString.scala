package ru.tomtrix.ttf

/**
 * fwsfes
 * @param source fsr
 */
class ExtendedString(source: String) {
  /**
   * fsefsef
   * @param that fsefse
   * @return
   */
  def ===(that: String) = {
    val x = if (source == null || source == None) "" else source.trim.toLowerCase
    val y = if (that == null || that == None) "" else that.trim.toLowerCase
    x equals y
  }

  /**
   * fsefse
   * @param that fsefse
   * @return
   */
  def !==(that: String) = !(===(that))
}

/**
 * fsfse
 */
object ExtendedString {
  implicit def toExtendedString(s: String) = new ExtendedString(s)
}
