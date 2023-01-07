package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.l.oklab.klimawatch.emissions.bo.Emissions
import de.l.oklab.klimawatch.emissions.bo.Sector
import de.l.oklab.klimawatch.emissions.to.EmissionsData
import de.l.oklab.klimawatch.emissions.to.EmissionsTO
import de.l.oklab.klimawatch.emissions.to.TimedData
import org.junit.jupiter.api.Test

class GroupTest {

    @Test
    fun testGroup() {
        val objectMapper = jacksonObjectMapper()
        val emissions = objectMapper.readValue(
            javaClass.getResource("/data/greenhouse-gas-emissions-leipzig.json"), EmissionsTO::class.java
        )
        val groupedBySector = emissions.data.groupBy { it.sector }


        val result = groupedBySector.map { createSector(it.key, it.value) }

        println(result)
    }

    private fun createSector(sectorName: String, emissions: List<EmissionsData>) {
        val sector = Sector(sectorName = sectorName, emissions = mutableListOf())
        sector.emissions.addAll(emissions.flatMap { emission -> createEmissions(emission, sector) })
    }

    private fun createEmissions(emission: EmissionsData, sector: Sector) =
        emission.data.map { timed -> createEmission(timed, sector) }

    private fun createEmission(timed: TimedData, sector: Sector): Emissions =
        Emissions(year = timed.year, value = timed.value, sector = sector)
}