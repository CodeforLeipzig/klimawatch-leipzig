package de.l.oklab.klimawatch.emissions.bo

import org.hibernate.annotations.Type
import org.intellij.lang.annotations.Identifier
import java.util.*

import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class Emissions(
    @Id
    val id: Long,
    @Column(unique = true, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    val externalId: UUID = UUID.randomUUID(),
    val year: Int,
    val sector: String,
    val value: Double
)

