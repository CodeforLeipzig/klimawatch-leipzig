package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.databind.ObjectMapper
import de.l.oklab.klimawatch.emissions.bo.Emissions
import de.l.oklab.klimawatch.emissions.bo.Sector
import de.l.oklab.klimawatch.emissions.to.EmissionsData
import de.l.oklab.klimawatch.emissions.to.EmissionsTO
import de.l.oklab.klimawatch.emissions.to.TimedData
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.stereotype.Service

@Service
class EmissionsService @ConstructorBinding constructor(
    internal val objectMapper: ObjectMapper,
    internal val repository: EmissionsRepository,
    internal val repositorySector: SectorRepository
) {

    var emissions: EmissionsTO? = null

    fun getAll(): EmissionsTO = getEmissionsData()

    fun getBySectorAndYear(sector: String?, year: Int?): List<EmissionsData> {
        val data = getEmissionsData()
        val filteredBySector = if (sector != null) data.data.filter { it.sector == sector } else data.data
        val filteredByYear = if (year != null) {
            filteredBySector.map {
                EmissionsData(
                    sector = it.sector,
                    data = it.data.filter { timedData -> timedData.year == year }
                )
            }
        } else filteredBySector
        return filteredByYear.filter { it.data.isNotEmpty() }
    }

    fun importData() {
        val sectors = mutableListOf<Sector>()
        val data = getEmissionsData()
        val groupedBySector = data.data.groupBy { it.sector }
        val result: List<Sector> = groupedBySector.map { createSector(it.key, it.value) }
        repositorySector.saveAll(result)
    }


    fun createSector(sectorName: String, emissions: List<EmissionsData>): Sector {
        val sector = Sector(sectorName = sectorName, emissions = mutableListOf())
        sector.emissions.addAll(emissions.flatMap { emission -> createEmissions(emission, sector) })
        return sector
    }

    fun createEmissions(emission: EmissionsData, sector: Sector) =
        emission.data.map { timed -> createEmission(timed, sector) }

    fun createEmission(timed: TimedData, sector: Sector): Emissions =
        Emissions(year = timed.year, value = timed.value, sector = sector)

    fun getEmissionsData(): EmissionsTO {
        return emissions ?: objectMapper.readValue(
            javaClass.getResource("/data/greenhouse-gas-emissions-leipzig.json"), EmissionsTO::class.java
        ).apply { emissions = this }
    }

    //TODO:Adjust to Sector as Entity
    fun getSectors(): List<String> {
        return repositorySector.getSectors()
    }

    //TODO:Pull years from database
    fun getYears(): List<Int> {
        return repository.getYears()
    }

    fun call() {
        println("after")
    }
}