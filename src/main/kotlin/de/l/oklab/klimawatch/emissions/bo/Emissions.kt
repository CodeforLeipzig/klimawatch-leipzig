package de.l.oklab.klimawatch.emissions.bo

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Emissions(
    @Id
    val id: Long,
    val year: Int,
    val sector: String,
    val value: Double
)