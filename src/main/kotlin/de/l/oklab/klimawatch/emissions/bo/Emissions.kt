package de.l.oklab.klimawatch.emissions.bo

data class Emissions(
    val year: Int,
    val sector: String,
    val value: Double
)