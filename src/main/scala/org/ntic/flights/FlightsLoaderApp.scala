package org.ntic.flights

import org.ntic.flights.data.{Flight, FlightsFileReport, Row}
import scala.util.Try
import java.io.File

object FlightsLoaderApp extends App {
  // 1. Obtener líneas del fichero
  val rawLines: Seq[String] = FileUtils.getLinesFromFile(FlightsLoaderConfig.filePath)

  // 2. Quitamos header si existe y filtramos líneas vacías o mal formadas
  val dataLines = if (FlightsLoaderConfig.hasHeaders && rawLines.nonEmpty) rawLines.tail else rawLines
  val validLines = dataLines.filterNot(FileUtils.isInvalidLine)

  // 3. Convertir a Try[Row]
  val rows: Seq[Try[Row]] = FileUtils.loadFromFileLines(validLines)

  // 4. Generar reporte
  val flightReport: FlightsFileReport = FlightsFileReport.fromRows(rows)

  // 5. Obtener lista limpia de vuelos
  val flights: Seq[Flight] = flightReport.flights

  // 6. Creamos la carpeta output para guardar los ficheros
  val outputDir = new File(FlightsLoaderConfig.outputDir)
  if (!outputDir.exists()) {
    outputDir.mkdirs() // Crea la carpeta (y subcarpetas si hiciera falta)
    println(s"Directorio creado: ${FlightsLoaderConfig.outputDir}")
  }

  // 7. Genermos los  ficheros por aeropuerto
  FlightsLoaderConfig.filteredOrigin.foreach { originCode =>

    // a. Filtrar vuelos de este origen
    val originFlights = flights.filter(_.origin.code == originCode)

    // b. Separar en retrasados y no retrasados usando isDelayed
    val (delayedFlights, notDelayedFlights) = originFlights.partition(_.isDelayed)

    // c. Ordenar (Flight implementa Ordered, así que sorted funciona directo)
    val sortedDelayed = delayedFlights.sorted
    val sortedNotDelayed = notDelayedFlights.sorted

    // d. Definir rutas de salida
    // Ejemplo: output/JFK_delayed.obj
    val delayedFlightsObj = s"${FlightsLoaderConfig.outputDir}/${originCode}_delayed.obj"
    // Ejemplo: output/JFK.obj
    val flightsObj = s"${FlightsLoaderConfig.outputDir}/${originCode}.obj"

    // e. Escribir en disco
    FileUtils.writeFile(sortedDelayed, delayedFlightsObj)
    FileUtils.writeFile(sortedNotDelayed, flightsObj)

    println(s"Generated files for $originCode")
  }
  println(flightReport)
}
