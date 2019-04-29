import sbt._

object Dependencies {

  object Versioning {
    lazy val sparkV = "2.4.0"
    lazy val awsV = "1.11.531"
    lazy val emrV = "5.23.0"
    lazy val js4sV = "3.6.5"
    lazy val xmlV = "1.2.0"
  }

  //TODO: Find out how to connect to emr repo.
  lazy val emrResolver = "EmrRepo" at
    s"https://s3.us-east-1.amazonaws.com/us-east-1-emr-artifacts/emr-${Versioning.emrV}/repos/maven/"

  object Artifacts {
    
    import Versioning._
    lazy val sparkSql = "org.apache.spark" %% "spark-sql" % sparkV % Provided
    lazy val sparkSqlKafka = "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkV % Provided
    lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test
    lazy val aws_emr = "com.amazonaws" % "aws-java-sdk-emr" % awsV % Test
    lazy val aws_s3 = "com.amazonaws" % "aws-java-sdk-s3" % awsV
    lazy val typeSafeConfig = "com.typesafe" % "config" % "1.3.3"
    lazy val json4s = "org.json4s" %% "json4s-native" % js4sV
    lazy val xml = "org.scala-lang.modules" %% "scala-xml" % xmlV
  }
}
