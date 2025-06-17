import sbt.*

object Libraries {
  val catsVersion            = "2.13.0"
  val catsEffectVersion      = "3.6.1"
  val scalaTestVersion       = "3.2.19"
  val http4sVersion          = "0.23.30"
  val circeVersion           = "0.14.13"
  val munitVersion           = "1.1.1"
  val logbackVersion         = "1.5.18"
  val munitCatsEffectVersion = "1.0.7"
  val ulidVersion            = "2025.1.14"
  val ironVersion            = "3.0.1"
  val xmlVersion             = "2.4.0"
  val tapirVersion           = "1.11.33"

  // Libraries
  lazy val cats       = "org.typelevel" %% "cats-core"   % catsVersion
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectVersion
  lazy val scalaTest =
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.18.1" % Test
  lazy val scalaTestCheck =
    "org.scalatestplus" %% "scalacheck-1-17" % "3.2.18.0" % Test
  lazy val http4s: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.http4s" %% "http4s-ember-client" % http4sVersion,
    "org.http4s" %% "http4s-circe"        % http4sVersion,
    "org.http4s" %% "http4s-dsl"          % http4sVersion
  )
  lazy val munit = "org.scalameta" %% "munit" % munitVersion % Test
  lazy val munitCatsEffect =
    "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffectVersion % Test
  lazy val logback =
    "ch.qos.logback" % "logback-classic" % logbackVersion
//  lazy val slf4j = "org.slf4j" % "slf4j-log4j12" % "2.0.16"
  lazy val slf4j = "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.24.3"

  lazy val ulid =
    "org.wvlet.airframe" %% "airframe-ulid" % ulidVersion
  lazy val circe = "io.circe" %% "circe-generic" % circeVersion

  lazy val iron     = "io.github.iltotore"     %% "iron"      % ironVersion
  lazy val ironCats = "io.github.iltotore"     %% "iron-cats" % ironVersion
  lazy val xml      = "org.scala-lang.modules" %% "scala-xml" % xmlVersion
  lazy val tapir: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-core"              % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"     % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-client"     % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe"        % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"      % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion
  )
  lazy val sttp: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % "0.11.9"
  )

  // Projects
  val common: Seq[ModuleID] =
    Seq(
      cats,
      scalaTest,
      scalaCheck,
      scalaTestCheck,
      ulid,
      catsEffect,
      iron,
      ironCats,
      xml,
      slf4j
    ) ++ http4s ++ tapir ++ sttp
  val chapter1: Seq[ModuleID] = Seq(catsEffect)
}
