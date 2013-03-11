package ru.tomtrix.ttf

import org.eclipse.swt.graphics.Point

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

  def comprises(that: String) = {
    val x = if (source == null || source == None) "" else source.trim.toLowerCase
    val y = if (that == null || that == None) "" else that.trim.toLowerCase
    x contains y
  }

  /**
   * fsefsefs
   * @param that esg
   * @return gdrgr
   */
  def doesntComprise(that: String) = !(comprises(that))
}

/**
 * njsfonfosresgg
 * @param source gsgser
 */
class ExtendedPoint(source: Point) {
  /**
   * gnjsorgsrggr
   * @param that gsgre
   * @return efw
   */
  def === (that: (Int, Int)) = source.x == that._1 && source.y == that._2

  /**
   * fsemmfselflgr
   * @param that grgsdr
   * @return vgser
   */
  def !== (that: (Int, Int)) = !(===(that))

  /**
   * ngsorgrs
   * @param a grs
   * @return gfs
   */
  def ++ (a: Int) = new Point(source.x + a, source.y + a)

  /**
   * nsolnrggr
   * @param a gssg
   * @return gs
   */
  def -- (a: Int) = ++(-a)
}

/**
 * fsfse
 */
object Implicits {
  /**
   * greioijgioer
   * @param s fse
   * @return
   */
  implicit def toExtendedString(s: String) = new ExtendedString(s)

  /**
   * fvjosejose
   * @param p ser
   * @return sger
   */
  implicit def toExtendedPoint(p: Point) = new ExtendedPoint(p)
}
