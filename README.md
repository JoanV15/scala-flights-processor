# Scala Flights Processor

Una aplicación en Scala para leer, limpiar y analizar datos de vuelos. Separa los vuelos retrasados de los puntuales.

## 📋 Requisitos
Para que funcione necesitas tener instalado:
* **Java** (JDK 11 o 17).
* **sbt** (Herramienta de construcción de Scala).

## 📂 Estructura del CSV
El archivo `flights.csv` debe usar **punto y coma (;)** como separador y tener estas 13 columnas:

`FL_DATE;ORIGIN_AIRPORT_ID;ORIGIN;ORIGIN_CITY_NAME;ORIGIN_STATE_ABR;DEST_AIRPORT_ID;DEST;DEST_CITY_NAME;DEST_STATE_ABR;DEP_TIME;DEP_DELAY;ARR_TIME;ARR_DELAY`

## 🚀 Puesta en marcha rápida

1.  **Descarga el proyecto:**
    ```bash
    git clone git@github.com:JoanV15/scala-flights-processor.git
    cd scala-flights-processor
    ```

2.  **Añade los datos (IMPORTANTE):**
    El archivo de datos no está incluido. Debes copiar tu archivo `flights.csv` en la carpeta raíz del proyecto (junto al `build.sbt`).
    * *Ojo: El CSV debe usar punto y coma (;).*

3.  **Ejecuta:**
    ```bash
    sbt clean run
    ```

## ✅ ¿Qué hace?
* Verás un resumen por consola (filas válidas vs inválidas).
* Se creará una carpeta `output` automáticamente con los archivos procesados (`.obj`).
