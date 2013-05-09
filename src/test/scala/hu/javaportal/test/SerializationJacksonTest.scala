package hu.javaportal.test

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, BeforeAndAfter}

import h2Context._
import com.fasterxml.jackson.databind.{DeserializationFeature, SerializationFeature, PropertyNamingStrategy, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import java.io.StringWriter
import java.util.HashMap
import net.fwbrasil.activate.entity.Var
import com.fasterxml.jackson.annotation.JsonIgnore

abstract class MixIn {
  @JsonIgnore var _varsMap: java.util.HashMap[String, Var[Any]] = null
}

object JsonSupport {
  val jsonMapper = new ObjectMapper {
    registerModule(DefaultScalaModule)
    setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
    addMixInAnnotations(classOf[Test], classOf[MixIn])
  }

  def parse[T](value: String)(implicit m: scala.Predef.Manifest[T]): Option[T] = {
    if (m.runtimeClass.isInstanceOf[Class[T]]) {
      Some(jsonMapper.readValue(value.getBytes, m.runtimeClass.asInstanceOf[Class[T]]))
    } else {
      None
    }
  }

  def json(value: Any): String = {
    val writer = new StringWriter
    jsonMapper.writeValue(writer, value)
    writer.toString
  }

  def apply(value: Any): String = {
    json(value)
  }
}

@RunWith(classOf[JUnitRunner])
class SerializationJacksonTest extends FunSuite with MockitoSugar with BeforeAndAfter {
  test("Correct result") {
    transactional {
      new Test("hello")
    }

    transactional {
      val roles = all[Test]
      JsonSupport.json(roles) === """[{"name":"hello"}]"""
    }
  }
  after {
    transactional {
      all[Test].foreach(_.delete)
    }
  }
}
