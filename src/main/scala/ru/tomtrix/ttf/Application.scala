package ru.tomtrix.ttf

import ru.tomtrix.ttf.patterns.Disposable

/**
 * fsefse
 */
class Application(db: DBMS) extends Disposable[Any] {
  def dispose() {println("good bye, baby!")}
}
