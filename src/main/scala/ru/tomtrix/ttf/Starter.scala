package ru.tomtrix.ttf

import ru.tomtrix.ttf.ExtendedString._

/**
 * Hello world!
 */
object Starter extends App {
  println( "11" == " 11 " )
  println( "11" === " 11 " )
  println( "  " === null )
  println( "  " !== null )
  println( "AB" !== "ab" )
  println( "AB" !== "5" )
}
