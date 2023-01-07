package de.l.oklab.klimawatch.emissions.bo

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
data class Emissions(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(unique = true, nullable = false)
        val externalId: String = UUID.randomUUID().toString(),
        val year: Int,
        @ManyToOne(
                fetch = FetchType.LAZY
        )
        @JoinColumn(name = "sector_id")
        val sector: Sector,
        val value: Double
)

