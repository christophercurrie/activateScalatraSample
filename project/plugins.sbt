import sbt._

import Defaults._

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers ++= Seq(
  "less is" at "http://repo.lessis.me",
  "coda" at "http://repo.codahale.com",
  "typesafe" at "http://repo.typesafe.com/typesafe/repo/",
  "Web plugin repo" at "http://siasia.github.com/maven2"
)

addSbtPlugin("me.lessis" % "ls-sbt" % "0.1.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.2.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.3")

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % "0.12.2-0.2.11")
