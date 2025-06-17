import Libraries.*

name := "FormalCalculation"

ThisBuild / scalaVersion := "3.7.0"

ThisBuild / semanticdbEnabled := true

lazy val commonSettings = Seq(
  run / fork := true,
  organization := "com.formalcalculation",
  version := "0.1.0-SNAPSHOT",
  libraryDependencies ++= Libraries.common,
  Compile / compile / wartremoverErrors ++= Seq(
    Wart.ArrayEquals,
    Wart.AnyVal,
    Wart.Enumeration,
    Wart.ExplicitImplicitTypes,
    Wart.FinalCaseClass,
    Wart.FinalVal,
    Wart.JavaConversions,
    Wart.LeakingSealed,
    Wart.NonUnitStatements,
    Wart.Product,
    Wart.Return,
    Wart.Serializable,
    Wart.StringPlusAny
  ),
  wartremoverExcluded += sourceManaged.value,
  scalacOptions ++= Seq(
    "UTF-8", // ファイルのエンコーディング
    "-deprecation", // 非推奨APIに関する警告
    "-unchecked", // 型チェックに関する警告
    "-feature", // 言語仕様の変更に関する警告
    "-explain-types", // 型エラー時に詳細な説明を表示
    "-Wunused:all", // 未使用コードに関する警告
    "-source:future", // 未来のバージョンの言語仕様を使用
  ),
  scalacOptions --= Seq(
    "-Ykind-projector",
    "-P:kind-projector:underscore-placeholders"
  )
)

scalafixAll := {}
scalafix := {}
Test / scalafix := {}

//lazy val scalafix_input = (project in file("scalafix/input"))
//  .disablePlugins(ScalafixPlugin)
//  .settings(libraryDependencies ++= Seq(cats))
//lazy val scalafix_output = (project in file("scalafix/output"))
//  .disablePlugins(ScalafixPlugin)
//  .settings(libraryDependencies ++= Seq(cats))
//lazy val scalafix_rules = (project in file("scalafix/rules"))
//  .disablePlugins(ScalafixPlugin)
//  .settings(
//    libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % _root_.scalafix.sbt.BuildInfo.scalafixVersion
//  )

lazy val abstractAlgebra = (project in file("abstract-algebra"))
  .settings(
    commonSettings
  )

lazy val arithmetic = (project in file("arithmetic"))
  .dependsOn(abstractAlgebra)
  .settings(
    commonSettings
  )

lazy val function = (project in file("function"))
//  .dependsOn(scalafix_rules % ScalafixConfig)
  .dependsOn(arithmetic)
  .settings(
    commonSettings
  )
