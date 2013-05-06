package hu.javaportal.test

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatra.test.scalatest.ScalatraFunSuite
import org.scalatest.mock.MockitoSugar
import org.scalatest.BeforeAndAfter

import h2Context._
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport
import org.json4s.{FieldSerializer, DefaultFormats, Formats}

class DeSerializationServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit def jsonFormats: Formats = DefaultFormats + FieldSerializer[AnyRef]()

  before() {
    contentType = formats("json")
  }

  post("/") {
    transactional {
      parsedBody.extract[Test]
    }
  }
}

@RunWith(classOf[JUnitRunner])
class DeSerializationTest extends ScalatraFunSuite with MockitoSugar with BeforeAndAfter {
  //Required  for entity loading before use
  transactional {}

  addServlet(new DeSerializationServlet, "/*")

  test("Deserialization problem") {
    post("/", """{"name":"test"}""", headers = Map("Content-Type" -> "application/json")) {
      status should equal(200)
    }
  }
}
