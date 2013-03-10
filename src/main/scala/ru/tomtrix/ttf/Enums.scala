package ru.tomtrix.ttf

/**
 * fsee
 */
abstract sealed class DBMS

case object SQLITE extends DBMS
case object MYSQL extends DBMS

object Keyboard {
  val BACKSPACE = 8
  val ENTER = 13
  val DELETE = 127
}

object Controls {
  val TXT_WIDTH = 120
  val TXT_HEIGHT = 22
  val BTN_WIDTH = 90
  val BTN_HEIGHT = 28
}
