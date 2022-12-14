package de.l.oklab.klimawatch.emissions

import de.l.oklab.klimawatch.emissions.bo.Emissions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmissionsRepository: JpaRepository<Emissions, Long> {


}