package org.ntic.flights.data

import scala.util.{Failure, Success, Try}

/**
 * This class is used to represent a report of the flights file with the valid rows, invalid rows and the flights
 * extracted from the valid rows.
 * @param validRows: Seq[Row]
 * @param invalidRows: Seq[String]
 * @param flights: Seq[Flight]
 */
case class FlightsFileReport(validRows: Seq[Row],
                         invalidRows: Seq[String],
                         flights: Seq[Flight]
                        ) {

  override val toString: String = {
    // Calculamos el resumen de errores
    val errorSummary = invalidRows
      .groupBy(identity) // Agrupamos por el mensaje de error idéntico
      .map { case (error, list) => s"<$error>: ${list.length}" } // Formato: <Error>: Cantidad
      .mkString("\n") // Unimos con saltos de línea

    s"""FlightsFileReport:
       |\t - ${validRows.length} valid rows.
       |\t - ${invalidRows.length} invalid rows.
       |Error summary:
       |$errorSummary
       |""".stripMargin
  }
}

object FlightsFileReport {
  /**
   * This function is used to create a FlightsFileReport from a list of Try[Row] objects where each Try[Row] represents a row
   * loaded from the file. If the row is valid, it is added to the validRows list, otherwise the error message is added to
   * the invalidRows list. Finally, the valid rows are converted to Flight objects and added to the flights list.
   *
   * @param rows: Seq[Try[Row]]
   * @return FlightsFileReport
   */
  def fromRows(rows: Seq[Try[Row]]): FlightsFileReport = {
    // 1. Filtramos las filas válidas (Success)
    val validRows = rows.collect { case Success(row) => row }

    // 2. Filtramos las filas inválidas (Failure) y extraemos el mensaje de error (toString de la excepción)
    val invalidRows = rows.collect { case Failure(e) => e.toString }

    // 3. Convertimos las filas válidas a Vuelos (Flights)
    val flights = validRows.flatMap { row =>
      Try(Flight.fromRow(row)).toOption
    }

    FlightsFileReport(validRows, invalidRows, flights)
  }
}
