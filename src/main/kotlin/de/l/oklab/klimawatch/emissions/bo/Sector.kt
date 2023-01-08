package de.l.oklab.klimawatch.emissions.bo

import org.hibernate.annotations.Type
import java.util.*
import jakarta.persistence.*



@Entity
data class Sector(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val externalId: String = UUID.randomUUID().toString(),
    @OneToMany(
        mappedBy = "sector",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val emissions: MutableList<Emissions> = ArrayList<Emissions>(),
    val sectorName: String


    )