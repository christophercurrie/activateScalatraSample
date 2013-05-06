package hu.javaportal.test

import h2Context._

case class Test(var name: String) extends Entity

class TestMigration extends Migration {
  def timestamp = 20130523001l

  def up = {
    table[Test].createTable(
      _.column[String]("name")
    )
  }
}

