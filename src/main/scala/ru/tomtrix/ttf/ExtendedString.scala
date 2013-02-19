package ru.tomtrix.ttf

/**
 * fwsfes
 * @param source fsr
 */
class ExtendedString(source: String) {
  /**
   * fsefsef
   * @param that fsefse
   * @return drgfes
   */
  def ===(that: String) = {
    val x = if (source == null || source == None) "" else source.trim.toLowerCase
    val y = if (that == null || that == None) "" else that.trim.toLowerCase
    x equals y
  }

  /**
   * fsefse
   * @param that fsefse
   * @return htrhgwe
   */
  def !==(that: String) = !(===(that))
}

/**
 * fsfse
 */
object ExtendedString {
  /**
   * greioijgioer
   * @param s fse
   * @return
   */
  implicit def toExtendedString(s: String) = new ExtendedString(s)

  /**
   * fwefjoijf
   * @param number t4ete4
   * @param one t4ete4
   * @param two te4te4
   * @param five te4t4e
   * @return y5
   */
  def normalize(number: Number)(one: String, two: String, five: String) = {
    val ends = (t: Int) => number.toString endsWith t.toString
    (number.intValue == number) match {
      case true =>
        if ((11 to 14) exists ends) five
        else if (ends(1)) one
        else if ((2 to 4) exists ends) two
        else five
      case false => two
    }
  }
}
