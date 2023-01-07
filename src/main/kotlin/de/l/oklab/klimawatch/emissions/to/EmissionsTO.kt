package de.l.oklab.klimawatch.emissions.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown=true)
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