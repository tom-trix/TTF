package ru.tomtrix.ttf.patterns

import java.sql.DriverManager
import org.apache.log4j.Logger
import collection.mutable.ArrayBuffer
import ru.tomtrix.ttf.{MYSQL, SQLITE, DBMS}
import ru.tomtrix.ttf.patterns.Disposable._

/**
 * gr
 */
class Repository(db: String)(dbms: DBMS) {
  val connection = dbms match {
    case SQLITE => {
      Class forName "org.sqlite.JDBC"
      DriverManager.getConnection("jdbc:sqlite:%s" format db)
    }
    case MYSQL => {
      throw new NotImplementedError
    }
  }

  private def prepare(sql: String, params: Seq[Any]) = {
    try {
      val ps = connection.prepareStatement(sql)
      (1 to params.length).zip(params) foreach {t => ps.setObject(t._1, t._2)}
      Some(ps)
    } catch {
      case e: Exception => Logger.getLogger(getClass).error("Error in database connection", e)
      None
    }
  }

  def getTable(sql: String, params: Seq[Any]) = {
    val result = ArrayBuffer[List[(String, Any)]]()
    for { st <- prepare(sql, params) } yield {
      using (st.executeQuery()) { rs =>
        val columns = (1 to rs.getMetaData.getColumnCount) map {rs.getMetaData.getColumnName(_)}
        while (rs.next())
          result += ((1 to columns.size).map {p => columns(p-1) -> rs.getObject(p)}.toList)
      }
    }
    result.toList
  }

  def getAttribute[T](sql: String, params: Seq[Any]) = {
    getTable(sql, params) map {_(0)._2.asInstanceOf[T]}
  }

  def getTuple(sql: String, params: Seq[Any]) = {
    val result = getTable(sql, params)
    if (result.size > 0) result(0)
    else List()
  }

  def getValue[T](sql: String, params: Seq[Any]) = {
    val result = getAttribute[T](sql, params)
    if (result.size > 0) Some(result(0))
    else None
  }

  def execute(sql: String, params: Seq[Any]) {
    prepare(sql, params) foreach {_.execute()}
  }

  def dispose() {connection.close(); println("connection closed")}
}
