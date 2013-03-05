package ru.tomtrix.ttf.patterns

import akka.actor.ActorSystem

/**
 * use it with "Using"
 */
trait Akka {
  val name: String
  lazy val system = ActorSystem(name)

  /**
   * use "using" instead
   */
  def close() {
    system shutdown()
    println("Akka closed")
  }
}