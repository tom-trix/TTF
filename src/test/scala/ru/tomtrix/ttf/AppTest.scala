import org.eclipse.swt.widgets.Display
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import ru.tomtrix.ttf.SQLITE
import ru.tomtrix.ttf.ExtendedString._
import ru.tomtrix.ttf.patterns.SafeCode._
import ru.tomtrix.ttf.patterns.Disposable._
import ru.tomtrix.ttf.patterns.{Undo, Repository, ActorsManager}

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

  feature("SafeCode") {
    info("SafeCode is a pattern that makes try-catch-finally code shorter (like tryo in Lift)")
    scenario("test a safe code") {
      val a = safe {
        1 + 4/2
      }
      expectResult(3)(a.getOrElse(-1))
    }
    scenario("test an unsafe code") {
      val a = safe {
        1 + 4/0
      }
      expectResult(-1)(a.getOrElse(-1))
    }
    scenario("test finally block") {
      var x = 1
      safe({x = 2}, {x = 0})
      expectResult(0)(x)
    }
  }

  feature("Undo") {
    info("Undo is a classic CTRL-Z pattern")
    scenario("test doCommand()") {
      val obj = new Object with Undo
      var s = "test"
      obj.doCommand({s = s toUpperCase()}, {s = s toLowerCase()})
      expectResult("TEST")(s)
      obj.undo()
      expectResult("test")(s)
      obj.redo()
      expectResult("TEST")(s)
      obj.undo()
      obj.undo()
      obj.undo()
      obj.undo()
      expectResult("test")(s)
    }
    scenario("test doChangeState()") {
      val obj = new Object with Undo
      var s = "test"
      val cmds = List("00", "11", "22", "33", "44", "55")
      cmds foreach {t => obj.doChangeState(s, t, (x:String) => s = x)}
      expectResult("55")(s)
      obj.undo()
      expectResult("44")(s)
      obj.undo()
      expectResult("33")(s)
      obj.redo()
      expectResult("44")(s)
      obj.undo()
      expectResult("33")(s)
      obj.undo()
      expectResult("22")(s)
      obj.undo()
      expectResult("11")(s)
      obj.undo()
      expectResult("00")(s)
      obj.undo()
      expectResult("test")(s)
      obj.undo()
      expectResult("test")(s)
      obj.undo()
      expectResult("test")(s)
      obj.redo()
      expectResult("00")(s)
    }
    scenario("test the stack size") {
      val obj = new Object with Undo
      obj.stackSize = 4
      var s = "test"
      val cmds = List("00", "11", "22", "33", "44", "55")
      cmds foreach {t => obj.doChangeState(s, t, (x:String) => s = x)}
      expectResult("55")(s)
      obj.undo()
      expectResult("44")(s)
      obj.undo()
      expectResult("33")(s)
      obj.undo()
      expectResult("22")(s)
      obj.undo()
      expectResult("11")(s)
      obj.undo()
      expectResult("11")(s)
      obj.undo()
      expectResult("11")(s)
      obj.undo()
      expectResult("11")(s)
      obj.undo()
      expectResult("11")(s)
      obj.redo()
      expectResult("22")(s)
    }
  }
}