package de.l.oklab.klimawatch.emissions.to

import com.fasterxml.jackson.annotation.JsonProperty

data class EmissionsTO(
    @JsonProperty("Daten")
    val data: List<EmissionsData>
)

data class EmissionsData(
    @JsonProperty("Sektor")
    val sector: String,
    @JsonProperty("Werte")
    val data: List<TimedData>
)

data class TimedData(
    @JsonProperty("Jahr")
    val year: Int,
    @JsonProperty("Wert")
    val value: Double
)