package ru.tomtrix.ttf.patterns

import java.sql.DriverManager
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
    val ps = connection.prepareStatement(sql)
    (1 to params.length).zip(params) foreach {t => ps.setObject(t._1, t._2)}
    ps
  }

  def getTable(sql: String, params: Seq[Any]) = {
    val result = ArrayBuffer[List[(String, Any)]]()
    using (prepare(sql, params) executeQuery()) { rs =>
      val columns = (1 to rs.getMetaData.getColumnCount) map {rs.getMetaData.getColumnName(_)}
      while (rs.next())
        result += ((1 to columns.size).map {p => columns(p-1) -> rs.getObject(p)}.toList)
    }
    result.toList
  }

  def getAttribute[T](sql: String, params: Seq[Any]) = {
    getTable(sql, params) map {_(0)._2.asInstanceOf[T]}
  }

  def getTuple(sql: String, params: Seq[Any]) = {
    getTable(sql, params)(0)
  }

  def getValue[T](sql: String, params: Seq[Any]) = {
    getAttribute[T](sql, params)(0)
  }

  def execute(sql: String, params: Seq[Any]) {
    prepare(sql, params) execute()
  }

  def dispose() {connection.close(); println("connection closed")}
}
