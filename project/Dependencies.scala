import sbt._

object Dependencies {

  lazy val sparkV = "2.4.0"
  lazy val awsV = "1.11.531"

  //TODO: Find out how to connect to emr repo.
  lazy val emrV = "5.23.0"
  lazy val emrResolver = "EmrRepo" at
    s"https://s3.us-east-1.amazonaws.com/us-east-1-emr-artifacts/emr-${emrV}/repos/maven/"

  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % sparkV % Provided
  lazy val sparkSqlKafka = "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkV % Provided
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test
  lazy val aws_emr = "com.amazonaws" % "aws-java-sdk-emr" % awsV % Test
  lazy val aws_s3 = "com.amazonaws" % "aws-java-sdk-s3" % awsV
}
