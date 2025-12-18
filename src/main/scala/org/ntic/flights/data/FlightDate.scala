/**
 * This class is used to represent a date of a flight
 * @param day: Int
 * @param month: Int
 * @param year: Int
 */

package org.ntic.flights.data

case class FlightDate(day: Int,
                      month: Int,
                      year: Int) {
  override lazy val toString: String = f"$day%02d/$month%02d/$year%04d"
}

object FlightDate {
  /**
   * This function is used to convert a string to a FlightDate
   * @param date: String
   * @return FlightDate
   */
  def fromString(date: String): FlightDate = {
    // El formato esperado es "mm/dd/yyyy hh:mm:ss PM|AM"
    // Primero nos quedamos solo con la parte de la fecha previa al primer espacio
    val datePart = date.split(" ")(0)

    // Luego convertimos "mm/dd/yyyy" a una lista de enteros
    datePart.split("/").map(_.toInt).toList match {
      case List(month, day, year) =>
        assert(month >= 1 && month <= 12, s"Invalid Month: $month")
        assert(day >= 1 && day <= 31, s"Invalid day: $day")
        assert(year >= 1987, s"Invalid year: $year (must be >= 1987)")

        FlightDate(day, month, year)

      case _ => throw new Exception(s"$date has an invalid format.")
    }
  }
}
