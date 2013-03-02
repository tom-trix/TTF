import org.eclipse.swt.widgets.Display
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import ru.tomtrix.ttf.SQLITE
import ru.tomtrix.ttf.ExtendedString._
import ru.tomtrix.ttf.patterns.{Repository, ActorsManager}
import ru.tomtrix.ttf.patterns.Disposable._

class AppTest extends FeatureSpec with GivenWhenThen {

  feature("Extended String") {
    info("Extended strings manage the troubles related to NULL, non-trim and case-sensitive strings")
    scenario("let's compare 2 strings") {
      expectResult(true)("hello" ≈ "hello")
      expectResult(true)("hello" ≈ "   hello   ")
      expectResult(true)("hello" ≈ "   HELLO   ")
      expectResult(true)("  HeLLo" ≈ "   HELLO   ")
      expectResult(true)("  " ≈ "")
      expectResult(true)("  " ≈ null)
      expectResult(true)("_" ≉ " ")
      expectResult(false)("_" ≈ " ")
    }
  }

  feature("Disposable trait") {
    info("Disposable is intended to close or/and dispose objects")
    scenario("let's test a close() method") {
      val obj = new Object {var x = true; def close() {x = false} }
      using(obj) { t =>
        expectResult(true)(t.x)
      }
      expectResult(false)(obj.x)
    }
    scenario("let's test a dispose() method") {
      val obj = new Object {var x = true; def dispose() {x = false} }
      using(obj) { t =>
        expectResult(true)(t.x)
      }
      expectResult(false)(obj.x)
    }
    scenario("let's test a double combination (close()/dispose())") {
      val display = new Display()
      using(display) { t =>
        expectResult(false)(t.isDisposed)
      }
      expectResult(true)(display.isDisposed)
    }
  }

  feature("ActorManager") {
    info("ActorManager is an Akka-object that uses Disposable feature")
    scenario("ensure the ActorManager is being closed after a usage") {
      using(ActorsManager) { t =>
        expectResult(false)(t.system.isTerminated)
      }
      Thread.sleep(500)
      expectResult(true)(ActorsManager.system.isTerminated)
    }
  }

  feature("Repository") {
    info("Repository is a pattern that provides a database abstraction level")
    scenario("first we gonna test queries") {
      using(new Repository("ttf.sqlite", SQLITE)) { db =>
        // clean failed tests
        db.execute("DELETE FROM Children WHERE name = ?", Seq("Test"))
        // come on
        expectResult(4)(db.getValue[Int]("SELECT COUNT(*) FROM Children").getOrElse(-1))
        val row = db.getTuple("SELECT * FROM Children WHERE age = ?", Seq(13))
        expectResult(3)(row.size)
        expectResult(row("name"))("Лёха")
        expectResult(row("age"))(13)
        val column = db.getAttribute[Int]("SELECT age FROM Children ORDER BY age DESC")
        expectResult(List(13, 12, 12, 10))(column)
        val table = db.getTable("SELECT * FROM Children WHERE age > ? GROUP BY age ORDER BY age", Seq(11))
        expectResult(12)(table(0)("age"))
        expectResult(13)(table(1)("age"))
      }
    }
    scenario("now let's test non-query executions") {
      using(new Repository("ttf.sqlite", SQLITE)) { db =>
        db.execute("INSERT INTO Children (name, age) VALUES (?, ?)", Seq("Test", 3))
        expectResult(5)(db.getValue("SELECT COUNT(*) FROM Children").getOrElse(-1))
        db.execute("DELETE FROM Children WHERE age = ?", Seq(3))
        expectResult(4)(db.getValue("SELECT COUNT(*) FROM Children").getOrElse(-1))
      }
    }
    scenario("finally let's test the exceptions") {
      using(new Repository("ttf.sqlite", SQLITE)) { db =>
        val table = db.getTable("SELECT * FROM Childs")
        expectResult(0)(table.size)
        val mySon = db.getValue[String]("SELECT name FROM Childs WHERE age < ", Seq(11))
        expectResult(None)(mySon)
      }
    }
  }
}