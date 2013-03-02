package ru.tomtrix.ttf.patterns

import java.sql.DriverManager
import scala.collection.mutable.ArrayBuffer
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.{MYSQL, SQLITE, DBMS}
import ru.tomtrix.ttf.patterns.Disposable._

/**
 * gr
 */
trait Repository {
  val db: String
  val dbms: DBMS
  lazy val sqlite = {
    Class forName "org.sqlite.JDBC"
    DriverManager.getConnection(s"jdbc:sqlite:$db")
  }

  val connection = dbms match {
    case SQLITE => sqlite
    case MYSQL => throw new NotImplementedError
    case _ => sqlite
  }

  /**
   * gergr
   * @param sql greg
   * @param params gerger
   * @return grege
   */
  private def prepare(sql: String, params: Seq[Any]) = {
    safe {
      val ps = connection.prepareStatement(sql)
      (1 to params.length).zip(params) foreach {p => ps.setObject(p._1, p._2)}
      ps
    }
  }

  /**
   * htrhyt
   * @param sql fsefs
   * @param params grsdgdh
   * @return jty
   */
  def getTable(sql: String, params: Seq[Any] = Nil) = {
    val result = ArrayBuffer[Map[String, Any]]()
    for { st <- prepare(sql, params) } yield {
      using (st.executeQuery()) { rs =>
        val columns = (1 to rs.getMetaData.getColumnCount) map {rs.getMetaData.getColumnName _}
        while (rs.next())
          result += (1 to columns.size).map{p => columns(p-1) -> rs.getObject(p)}.toMap
      }
    }
    result.toList
  }

  /**
   * gerhrth
   * @param sql fsef
   * @param params hfrthf
   * @tparam T hfthf
   * @return seges
   */
  def getAttribute[T](sql: String, params: Seq[Any] = Nil) = {
    getTable(sql, params) map {_.toList(0)._2.asInstanceOf[T]}
  }

  /**
   * gresfe
   * @param sql hdrthd
   * @param params gsegse
   * @return gdgs
   */
  def getTuple(sql: String, params: Seq[Any] = Nil) = {
    val result = getTable(sql, params)
    if (result.size > 0) result(0)
    else Map.empty[String, Any]
  }

  /**
   * fsegrd
   * @param sql gdrgrsd
   * @param params fsefse
   * @tparam T gsfges
   * @return gseges
   */
  def getValue[T](sql: String, params: Seq[Any] = Nil) = {
    val result = getAttribute[T](sql, params)
    if (result.size > 0) Some(result(0))
    else None
  }

  /**
   * fgesgseg
   * @param sql esgg
   * @param params fawf
   */
  def execute(sql: String, params: Seq[Any] = Nil) {
    prepare(sql, params) foreach {_.execute()}
  }

  /**
   * use "using" instead
   */
  def dispose() {connection.close(); println("connection closed")}
}
