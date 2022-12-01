package de.l.oklab.klimawatch.emissions.bo

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*



@Entity
data class Sector(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    val externalId: UUID = UUID.randomUUID(),
    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val comments: List<Emissions> = ArrayList<Emissions>(),
    val sectorName: String
    )