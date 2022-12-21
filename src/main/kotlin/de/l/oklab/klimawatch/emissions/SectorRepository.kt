package de.l.oklab.klimawatch.emissions

import de.l.oklab.klimawatch.emissions.bo.Sector
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SectorRepository: JpaRepository<Sector,Long>{
}