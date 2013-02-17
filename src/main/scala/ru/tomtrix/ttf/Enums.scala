package ru.tomtrix.ttf

/**
 * fsee
 */
abstract sealed class DBMS

object SQLITE extends DBMS
object MYSQL extends DBMS
