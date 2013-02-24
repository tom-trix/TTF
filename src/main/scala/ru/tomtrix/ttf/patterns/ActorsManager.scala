package ru.tomtrix.ttf.patterns

import akka.actor.ActorSystem

/**
 * use it with "Using"
 */
object ActorsManager {
  val system = ActorSystem("TrixSystem")

  def close() {
    system shutdown()
  }
}