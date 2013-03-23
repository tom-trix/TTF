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
  val STD_WIDTH = 100
  val AUTOCOMPLETE_WIDTH = 200
  val AUTOCOMPLETE_HEIGHT = 100
  val STD_MARGIN = 10
}
