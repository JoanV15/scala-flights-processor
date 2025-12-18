package org.ntic.flights

import org.ntic.flights.data.{Flight, Row}
import java.io.{FileOutputStream, ObjectOutputStream}
import scala.io.Source
import scala.util.Try

object FileUtils {

  /**
   * This function is used to check if the line is valid or not
   * @param s: String
   * @return Boolean: true if the line is invalid, false otherwise
   */
  def isInvalidLine(s: String): Boolean = {
    // Si la línea está vacía o solo tiene espacios no es correcta
    if (s.trim.isEmpty) {
      true
    } else {
      // Hacemos split por el delimitador configurado
      val tokens = s.split(FlightsLoaderConfig.delimiter, -1)

      // Es inválida si el número de tokens NO coincide con el número de cabeceras esperado
      tokens.length != FlightsLoaderConfig.headersLength
    }
  }

  /**
   * This function is used to read the file located in the path `filePath` and return a list of lines of the file
   *
   * @param filePath: String
   * @return List[String]
   */
  def getLinesFromFile(filePath: String): List[String] = {
    val source = Source.fromFile(filePath)
    try {
      // Obtenemos las líneas y las convertimos a una List de Scala
      source.getLines().toList
    } finally {
      // Importante cerrar el recurso para no dejar ficheros abiertos en el sistema
      source.close()
    }
  }

  /**
   * This function is used to load the rows from the file lines
   *
   * @param fileLines: Seq[String]
   * @return Seq[Try[Row]]
   */
  def loadFromFileLines(fileLines: Seq[String]): Seq[Try[Row]] = {
    fileLines.map { line =>
      val tokens = line.split(FlightsLoaderConfig.delimiter, -1)
      Row.fromStringList(tokens)
    }
  }

  def writeFile(flights: Seq[Flight], outputFilePath: String): Unit = {
    val out = new ObjectOutputStream(new FileOutputStream(outputFilePath))
    try {
      out.writeObject(flights)
    } finally {
      out.close()
    }
  }
}