package org.ntic.flights

import com.typesafe.config.{Config, ConfigFactory}
import scala.jdk.CollectionConverters._

object FlightsLoaderConfig {
  private val applicationConf: Config = ConfigFactory.load()
  val config: Config = applicationConf.getConfig("flightsLoader")

  val filePath: String = config.getString("filePath")
  val hasHeaders: Boolean = config.getBoolean("hasHeaders")
  val headersLength: Int = config.getInt("headersLength")
  val delimiter: String = config.getString("delimiter")
  val outputDir: String = config.getString("outputDir")
  val headers: List[String] = config.getStringList("headers").asScala.toList

  val columnIndexMap: Map[String, Int] = headers.map(x => (x, headers.indexOf(x))).toMap
  val filteredOrigin: List[String] = config.getStringList("filteredOrigin").asScala.toList
}
