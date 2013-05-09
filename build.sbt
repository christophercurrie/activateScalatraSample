crossScalaVersions := Seq("2.10.1")

scalaVersion <<= (crossScalaVersions) {
      versions => versions.head
    }

resolvers ++= Seq(Opts.resolver.sonatypeSnapshots, Opts.resolver.sonatypeReleases)

resolvers += "Activate Framework" at "http://fwbrasil.net/maven/"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo/"

resolvers += "fasterxml" at "https://oss.sonatype.org/content/repositories/comfasterxml-046/"

libraryDependencies ++= Seq(
    "net.fwbrasil" %% "activate-jdbc" % "1.2.1",
    "net.fwbrasil" %% "activate-core" % "1.2.1",
    "org.scalatra" %% "scalatra-json" % "2.2.0",
    "org.scalatra" %% "scalatra-scalatest" % "2.2.0" % "test",
    "org.json4s" %% "json4s-jackson" % "3.2.4",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.2.1",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.1",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.2.1",
    "com.h2database" % "h2" % "1.3.170"
)

