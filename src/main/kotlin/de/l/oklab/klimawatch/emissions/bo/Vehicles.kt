package de.l.oklab.klimawatch.emissions.bo

import jakarta.persistence.*
import java.util.*

@Entity
data class Vehicles(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val externalId: String = UUID.randomUUID().toString(),
    val quantity: Int,
    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "vehicle_id")
    val sector: Sector,
    val year: Int
)