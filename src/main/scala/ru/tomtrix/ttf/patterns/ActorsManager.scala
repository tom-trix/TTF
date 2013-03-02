package ru.tomtrix.ttf.patterns

import akka.actor.ActorSystem

/**
 * use it with "Using"
 */
object ActorsManager {
  val system = ActorSystem("TrixSystem")

  /**
   * use "using" instead
   */
  def close() {
    system shutdown()
  }
}