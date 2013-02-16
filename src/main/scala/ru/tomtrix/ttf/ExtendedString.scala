package ru.tomtrix.ttf

/**
 * ger
 */
class ExtendedString(source: String) {
  def ===(that: String) = {
    val x = if (source == null || source == None) "" else source.trim.toLowerCase
    val y = if (that == null || that == None) "" else that.trim.toLowerCase
    x equals y
  }

  def !==(that: String) = !(===(that))
}

object ExtendedString {
  implicit def toExtendedString(s: String) = new ExtendedString(s)
}
