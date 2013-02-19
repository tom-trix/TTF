package ru.tomtrix.ttf.patterns

import org.apache.log4j.Logger

/**
 * te4
 */
object SafeCode {
  val emptyFunc = () => {}

  /**
   * feuwfu
   * @param func hkrop
   * @tparam T fwfhwio
   * @return
   */
  def safe[T](func: () => T, finallyFunc: () => Unit = emptyFunc): Option[T] = {
    try {
      Some(func())
    }
    catch {
      case e: Exception => Logger.getLogger(getClass).error("SafeCode error", e)
      None
    }
    finally {
      finallyFunc()
    }
  }
}
