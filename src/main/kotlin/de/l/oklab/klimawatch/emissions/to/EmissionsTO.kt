package de.l.oklab.klimawatch.emissions.to

import com.fasterxml.jackson.annotation.JsonProperty
import de.l.oklab.klimawatch.emissions.bo.Emissions
import de.l.oklab.klimawatch.emissions.bo.Sector

data class EmissionsTO(
    @JsonProperty("Daten")
    val data: List<EmissionsData>
) {
    fun toEntities(): List<Emissions> {
        return data.flatMap { it.toEntities() }
    }
}

data class EmissionsData(
    @JsonProperty("Sektor")
    val sector: String,
    @JsonProperty("Werte")
    val data: List<TimedData>
) {

    fun toEntities(): List<Emissions> {
        return data.map { it.toEntity(sector) }
    }
}

data class TimedData(
    @JsonProperty("Jahr")
    val year: Int,
    @JsonProperty("Wert")
    val value: Double
) {

    fun toEntity(sector: String): Emissions = Emissions(sector = Sector(sectorName=sector), year = year, value = value)
}