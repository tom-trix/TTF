import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import ru.tomtrix.ttf.ExtendedString._
import ru.tomtrix.ttf.patterns.ActorsManager
import ru.tomtrix.ttf.patterns.Disposable._

class AppTest extends FeatureSpec with GivenWhenThen {

  feature("Extended String") {
    info("Extended strings manage the troubles related to NULL, non-trim and case-sensitive strings")
    scenario("let's compare 2 strings") {
      var a = "hello"
      var b = "hello"
      assert(a ≈ b)

      b = " HeLLo   "
      assert(a ≈ b)

      b = null
      assert(a ≉ b)

      a = "  "
      assert(a ≈ b)
    }
  }

  feature("Disposable & ActorManager") {
    info("Disposable is intended to close or/and dispose objects")
    info("ActorManager is an Akka-object that uses Disposable feature")
    scenario("let's test a close() method") {
      using(ActorsManager) { t =>
        expectResult(false)(t.system.isTerminated)
      }
      Thread.sleep(500)
      assert(ActorsManager.system.isTerminated)
    }
    scenario("let's test a dispose() method") {
      val g = new Object {var x = true; def dispose() {x = false} }
      using(g) { t =>
        assert(t.x)
      }
      expectResult(false)(g.x)
    }
  }
}