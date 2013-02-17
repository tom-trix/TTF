package ru.tomtrix.ttf

import patterns.Disposable

/**
 * fsefse
 */
class Application(db: DBMS) extends Disposable[Any] {
  def dispose() {println("good bye, baby!")}
}
