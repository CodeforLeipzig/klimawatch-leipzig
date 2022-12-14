package de.l.oklab.klimawatch.emissions

import de.l.oklab.klimawatch.emissions.bo.Sector
import org.springframework.data.jpa.repository.JpaRepository

interface SectorRepository: JpaRepository<Sector,Long>{
}