package ru.tomtrix.ttf

import java.text.MessageFormat
import java.util.ResourceBundle
import ru.tomtrix.ttf.Wrappers._
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
  val ERROR = "Error! Cannot interpolate the string: "
  private var defaultBundle: I18n = null
  private var ttfBundle = new I18n("ttf_ru")

  /**
   * fmen
   */
  val format = (s: String, i18n: I18n, args: Seq[AnyRef]) => safe {
    val z = for {value <- i18n.bundle map {_ getString s}}
            yield MessageFormat.format(value, args: _*)
    z getOrElse s"${ERROR}$s"
  } getOrElse s"${ERROR}$s"

  /**
   * fweofnmjw
   * @param sc ewfew
   * @return wergwer
   */
  private def fuse(sc: StringContext) = sc.parts.foldLeft("") {(a, b) => s"${a}$b"}

  /**
   * fenslkfnse
   * @param x sefsegf
   */
  def setDefaultBundle(x: I18n) {
    defaultBundle = x
  }

  /**
   * fsnleknff
   * @param x afwfgr
   */
  def setTtfBundle(x: I18n) {
    ttfBundle = x
  }

  /**
   * fseojseoffe
   * @param sc fse
   */
  implicit class I18nInterpoler(val sc: StringContext) extends AnyVal {
    /**
     * fnseffes
     * @param args dhta
     * @return gerger
     */
    def i(args: Any*): String = format(fuse(sc), defaultBundle, args map {_ toString()})

    /**
     * nsflngldgnrg
     * @param args gergr
     * @return gse
     */
    def i125(args: Any*): String = {
      val ends = (t: Int) => args(0).toString endsWith t.toString
      if (args.size == 1) {
        val postfix = if (isWhole(args(0)))
                        if ((11 to 14) exists ends) "5"
                        else if (ends(1)) "1"
                        else if ((2 to 4) exists ends) "2"
                        else "5"
                      else if (isFractional((args(0))))"2" else ""
        format(s"${fuse(sc)}$postfix", defaultBundle, args map {_ toString()})
      }
      else s"${ERROR}$sc"
    }

    /**
     * fnosengesrgg
     * @param args gbreg
     * @return wsgferw
     */
    def i12(args: Any*): String = {
      val ends = (t: Int) => args(0).toString endsWith t.toString
      if (args.size == 1) {
        val postfix = if (isWhole(args(0)))
                        if (ends(1) && !ends(11)) "1" else "2"
                      else if (isFractional(args(0))) "1" else ""
        format(s"${fuse(sc)}$postfix", defaultBundle, args map {_ toString()})
      }
      else s"${ERROR}$sc"
    }

    /**
     * jgosergerwwwe
     * @param args gwrfe
     * @return gwegwej
     */
    def ttf(args: Any*): String = format(fuse(sc), ttfBundle, args map {_ toString()})
  }
}

