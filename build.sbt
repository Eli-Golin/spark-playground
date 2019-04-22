
import Dependencies.Artifacts._
lazy val commonSettings = Seq(
  scalaVersion := "2.11.8",
  organization := "com.clicktale.cec",
  libraryDependencies ++= Seq(scalaTest,typeSafeConfig)
)

lazy val commonSparkSettings  = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
    case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
    case PathList("org", "apache", xs @ _*) => MergeStrategy.last
    case PathList("com", "google", xs @ _*) => MergeStrategy.last
    case "about.html" => MergeStrategy.rename
    case "plugin.properties" => MergeStrategy.last
    case "log4j.properties" => MergeStrategy.last
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  libraryDependencies ++= Seq(
    sparkSql,
    sparkSqlKafka
  )
)

lazy val cecUnifiedJob = (project in file("cec-unified-job")).settings(
  commonSettings,
  name := "cec-unified-job",
  commonSparkSettings
).dependsOn(common)

lazy val coreUnifiedJob = (project in file("core-unified-job")).settings(
  commonSettings,
  name := "core-unified-job",
  commonSparkSettings
).dependsOn(common)

lazy val matchingUnifiedJob = (project in file("matching-unified-job")).settings(
  commonSettings,
  name := "matching-unified-job",
  commonSparkSettings
).dependsOn(common)

lazy val common = (project in file("common")).settings(
  commonSettings,
  name := "common"
)

lazy val root = (project in file (".")).aggregate(cecUnifiedJob).aggregate(coreUnifiedJob).aggregate(matchingUnifiedJob)

