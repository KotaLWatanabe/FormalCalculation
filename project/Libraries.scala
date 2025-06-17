import sbt.*

object Libraries {
  val catsVersion            = "2.13.0"
  val scalaTestVersion       = "3.2.19"
  val munitVersion           = "1.1.1"
  val logbackVersion         = "1.5.18"

  // Libraries
  lazy val cats       = "org.typelevel" %% "cats-core"   % catsVersion
  lazy val scalaTest =
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.18.1" % Test
  lazy val scalaTestCheck =
    "org.scalatestplus" %% "scalacheck-1-17" % "3.2.18.0" % Test
  lazy val munit = "org.scalameta" %% "munit" % munitVersion % Test
  lazy val logback =
    "ch.qos.logback" % "logback-classic" % logbackVersion
  lazy val slf4j = "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.25.0"

  // Projects
  val common: Seq[ModuleID] =
    Seq(
      cats,
      scalaTest,
      scalaCheck,
      scalaTestCheck,
      slf4j
    )
}
