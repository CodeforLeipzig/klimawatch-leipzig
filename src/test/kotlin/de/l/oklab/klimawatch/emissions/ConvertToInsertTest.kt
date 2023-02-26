package de.l.oklab.klimawatch.emissions

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileWriter
import java.nio.file.Files

class ConvertToInsertTest {

    @Test
    @Disabled
    fun test() {
       val file = File("/home/joerg/Schreibtisch/rain.csv")
       val lines = Files.readAllLines(file.toPath())
       val insert = "INSERT INTO station_values (station_id, date, mm) VALUES "
       val inserts = lines.map { it.split(";") }.joinToString(", \n") { "(1, '${toDateStr(it[0])}', ${it[1]})" }
       FileWriter(File("/home/joerg/Schreibtisch/rain_insert.sql")).use { it.write("$insert\n$inserts") }
    }

    private fun toDateStr(date: String): String {
        val parts = date.split(".")
        return if (parts.size == 3) "${parts[2]}-${parts[1]}-${parts[0]}" else "unknown"
    }
}