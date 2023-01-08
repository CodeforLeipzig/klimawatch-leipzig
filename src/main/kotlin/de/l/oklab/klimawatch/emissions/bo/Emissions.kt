package de.l.oklab.klimawatch.emissions.bo

import org.hibernate.annotations.UuidGenerator
import java.util.*
import jakarta.persistence.*

@Entity
data class Emissions(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @GeneratedValue
        @UuidGenerator(style = UuidGenerator.Style.TIME)
        @Column(unique = true, nullable = false)
        val externalId: UUID? = null,
        val year: Int,
        @ManyToOne(
                fetch = FetchType.LAZY
        )
        @JoinColumn(name = "sector_id")
        val sector: Sector,
        val value: Double
)

