package ru.tomtrix.ttf.forms

import org.eclipse.swt.widgets.Control

/**
 * fse
 */
trait Element {
  val title: String
  val control: Control
  val default: Any

  def getResult: Any
}
