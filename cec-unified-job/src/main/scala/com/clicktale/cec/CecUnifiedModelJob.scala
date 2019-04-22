package main.scala.com.clicktale.cec

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{Dataset, SparkSession}

object CecUnifiedModelJob {
  private val config = ConfigFactory.load().getConfig("com.clicktale.cec")

  def main(args:Array[String]): Unit = {
    val sparkSession = SparkSession.builder.appName("job1.1").config("spark.master", "local[4]").getOrCreate()
    val dataset = configureInputKafkaStream(sparkSession)
    val fullStream = connectToConsoleSink(dataset)
    fullStream.start().awaitTermination()
  }

  private def configureInputKafkaStream(ss:SparkSession) = {
    import org.apache.spark.sql.functions.col
    import ss.implicits._
    ss.readStream.format("kafka").
      option("kafka.bootstrap.servers",config.getString("kafka.servers")).
      option("subscribe",config.getString("kafka.topic")).
      option("kafkaConsumer.pollTimeoutMs",config.getLong("kafka.pollTimeoutMillis")).
      load().select(col("key").cast(StringType), col("value").cast(StringType)).as[(String,String)]
  }

//  private def connectToConsoleSink(ds:DataFrame) = {
//    ds.printSchema()
//    ds.writeStream.outputMode("append").format("console")
//  }

  private def connectToConsoleSink(ds:Dataset[(String,String)]) = {
    ds.printSchema()
    ds.writeStream.outputMode("append").format("console")
  }
}
