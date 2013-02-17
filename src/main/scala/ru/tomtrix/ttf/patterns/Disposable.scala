package ru.tomtrix.ttf.patterns

/**
 * @author http://blog.omega-prime.co.uk/?p=17
 */
trait Disposable[T] {
  def dispose()
}

object Disposable {
  implicit def close2disposable[T](source: T { def close() }) : Disposable[T] =
    new Disposable[T] {
      def dispose() {
        try { source.close() }
        catch { case t: Throwable => () }
      }
    }

  implicit def dispose2disposable[T](source: T { def dispose() }) : Disposable[T] =
    new Disposable[T] {
      def dispose() {
        source.dispose()
      }
    }

  def using[T <% Disposable[T], V](what: T)(func: T => V): V = {
    try {
      func(what)
    }
    finally {
      what.dispose()
    }
  }
}