package com.clicktale.cec


import com.clicktale.cec.cum.model.KafkaMessages.CecUnifiedVisitId
import com.clicktale.cec.model.{CecPageViewMessage, KafkaCecVisitMessage}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Dataset, SparkSession}
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write


object CecUnifiedModelJob {
  private val config = ConfigFactory.load().getConfig("com.clicktale.cec")
  implicit val formats = DefaultFormats

  def main(args:Array[String]): Unit = {
    val sparkSession = if(config.getBoolean("local"))
                          SparkSession.builder.appName("job1.1").config("spark.master", "local[4]").getOrCreate()
                      else
                          SparkSession.builder.appName("job1.1").getOrCreate()
    val dataset = connectToKafkaSource(sparkSession)
    val logic = defineLogic(dataset,sparkSession)
    val kafkaToKafkaStream = connectToKafkaSink(logic)
    kafkaToKafkaStream.start().awaitTermination()
  }

  private def connectToKafkaSource(ss:SparkSession) = {
    import ss.implicits._

    ss.readStream.format("kafka").
      option("kafka.bootstrap.servers",config.getString("ceckafka.servers")).
      option("subscribe",config.getString("ceckafka.topic")).
      option("kafkaConsumer.pollTimeoutMs",config.getLong("ceckafka.pollTimeoutMillis")).
      load().select(col("value").cast(StringType)).as[String]
  }

  private def connectToConsoleSink[A](ds:Dataset[A],options:Map[String,String] = Map.empty) = {
    ds.printSchema()
    ds.writeStream.outputMode("append").options(options).format("console")
  }

  private def connectToKafkaSink(ds:Dataset[(String,String)]) = {
    ds.select(col("_1").as("key").cast(StringType),col("_2").as("value").cast(StringType)).
      writeStream.format("kafka").
      option("kafka.bootstrap.servers",config.getString("cec_destination_kafka.servers")).
      /* Have to specify a checkpoint to kafka sink according to:
         https://stackoverflow.com/questions/50936964/sparkstreaming-avoid-checkpointlocation-check
      */
      option("checkpointLocation", config.getString("cec_destination_kafka.checkpointLocation")).
      option("topic", config.getString("cec_destination_kafka.topic"))
  }

  private def defineLogic(ds:Dataset[String],ss:SparkSession) = {
    import ss.implicits._
    /*
      Need a special encoder for the Either type since does not implement Serializable in scala 2.11
     */
    implicit val eitherEncoder = org.apache.spark.sql.Encoders.kryo[Either[CecPageViewMessage,KafkaCecVisitMessage] with Product with Serializable]

    ds.map(m => parse(m)).map { js =>
      implicit val formats = Serialization.formats(NoTypeHints)
      val json = js \ "entityType"
      if(json.extract[Byte] == 0) Left(json.extract[CecPageViewMessage]) else Right(json.extract[KafkaCecVisitMessage])
    }.map {
      case Left(x) =>
        val key = ""
        val value = s"""{"projectId":${x.projectId},"visitorId":${x.visitorId},"visitId":${x.visitId},"pageViewId:${x.pageViewId}"""
        key -> value
      case Right(y) =>
        val key = s"""{"projectId":${y.projectId},"visitorId":${y.visitorId},"visitId":${y.visitId}"""
        val value = write(CecUnifiedVisitId(y.dispatchTimestamp,y.projectId,y.visitorId,y.visitId,y.pageViewIds))
        key -> value
    }
  }
}
