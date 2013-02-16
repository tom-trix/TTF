import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen
import ru.tomtrix.ttf.ExtendedString._

class AppTest extends FeatureSpec with GivenWhenThen {

  feature("Extended String") {

    info("Extended strings manage the troubles related to NULL, non-trim and case-sensitive strings")

    scenario("given different non-null strings") {
      var a = "hello"
      var b = "hello"
      assert(a === b)

      b = " HeLLo   "
      assert(a === b)
    }
  }
}