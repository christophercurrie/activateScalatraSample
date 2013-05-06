package hu.javaportal.test

import org.scalatra.ScalatraServlet
import org.json4s.{FieldSerializer, DefaultFormats, Formats}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatra.test.scalatest.ScalatraFunSuite
import org.scalatest.mock.MockitoSugar
import org.scalatest.BeforeAndAfter
import net.fwbrasil.activate.entity.Var
import org.scalatra.json.JacksonJsonSupport

trait SimplePrivateBad extends Serializable {
  @transient private var _boo = "Asd"
  @transient
  private var _baseVar: Var[Any] = null
}

case class MyTestBad(var name: String) extends SimplePrivateBad


trait SimplePrivateOk extends Serializable {
  @transient private var _boo = "Asd"
}

case class MyTestOk(var name: String) extends SimplePrivateOk

class SerializationServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit def jsonFormats: Formats = DefaultFormats + FieldSerializer[AnyRef]()

  before() {
    contentType = formats("json")
  }
  get("/bad") {
    MyTestBad("Bad")
  }
  get("/ok") {
    MyTestOk("Ok")
  }
}

@RunWith(classOf[JUnitRunner])
class SerializationTest extends ScalatraFunSuite with MockitoSugar with BeforeAndAfter {
  addServlet(new SerializationServlet, "/*")
  test("Correct result") {
    get("/ok", headers = Map("Content-Type" -> "application/json")) {
      status should equal(200)
    }
  }
  test("Internal error") {
    get("/bad", headers = Map("Content-Type" -> "application/json")) {
      status should equal(200)
    }
  }
}
