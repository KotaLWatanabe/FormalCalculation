import sbt.*

object Libraries {
  val catsVersion       = "2.13.0"
  val catsEffectVersion = "3.6.1"
  val http4sVersion     = "0.23.30"
  val circeVersion      = "0.14.10"
  val scalaTestVersion  = "3.2.19"
  val munitVersion      = "1.1.1"
  val logbackVersion    = "1.5.18"

  // Libraries
  lazy val cats              = "org.typelevel" %% "cats-core"           % catsVersion
  lazy val catsEffect        = "org.typelevel" %% "cats-effect"         % catsEffectVersion
  lazy val http4sDsl         = "org.http4s"    %% "http4s-dsl"          % http4sVersion
  lazy val http4sEmberServer = "org.http4s"    %% "http4s-ember-server" % http4sVersion
  lazy val http4sEmberClient = "org.http4s"    %% "http4s-ember-client" % http4sVersion
  lazy val http4sCirce       = "org.http4s"    %% "http4s-circe"        % http4sVersion
  lazy val circeCore         = "io.circe"      %% "circe-core"          % circeVersion
  lazy val circeGeneric      = "io.circe"      %% "circe-generic"       % circeVersion
  lazy val circeParser       = "io.circe"      %% "circe-parser"        % circeVersion
  lazy val scalaTest =
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.18.1" % Test
  lazy val scalaTestCheck =
    "org.scalatestplus" %% "scalacheck-1-17" % "3.2.18.0" % Test
  lazy val munit = "org.scalameta" %% "munit" % munitVersion % Test
  lazy val logback =
    "ch.qos.logback" % "logback-classic" % logbackVersion
  lazy val slf4j = "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.25.1"

  // Projects
  val common: Seq[ModuleID] =
    Seq(
      cats,
      scalaTest,
      scalaCheck,
      scalaTestCheck,
      slf4j
    )

  val backend: Seq[ModuleID] =
    Seq(
      catsEffect,
      http4sDsl,
      http4sEmberServer,
      http4sEmberClient,
      http4sCirce,
      circeCore,
      circeGeneric,
      circeParser,
      logback
    )
}
