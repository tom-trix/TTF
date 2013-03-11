package ru.tomtrix.ttf

/**
 * fsee
 */
abstract sealed class DBMS

case object SQLITE extends DBMS
case object MYSQL extends DBMS

abstract sealed class CHECKSTATE

case object CHECKED extends CHECKSTATE
case object UNCHECKED extends CHECKSTATE
case object GRAYED extends CHECKSTATE

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
  val CHECK_WIDTH = 90
  val CHECK_HEIGHT = 18
  val RADIOGROUP_WIDTH = 120
  val RADIOGROUP_HEIGHT = 100
  val RADIO_WIDTH = 90
  val RADIO_HEIGHT = 20
  val AUTOCOMPLETE_WIDTH = 200
  val AUTOCOMPLETE_HEIGHT = 100
  val TEXT_LIMIT = 1 << 14
  val STD_MARGIN = 10
}
