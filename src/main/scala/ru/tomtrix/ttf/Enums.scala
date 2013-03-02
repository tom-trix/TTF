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