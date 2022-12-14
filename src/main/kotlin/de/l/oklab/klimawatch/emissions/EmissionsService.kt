package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.databind.ObjectMapper
import de.l.oklab.klimawatch.emissions.bo.Sector
import de.l.oklab.klimawatch.emissions.to.EmissionsData
import de.l.oklab.klimawatch.emissions.to.EmissionsTO
import org.springframework.boot.context.properties.ConstructorBinding
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
        val data = getEmissionsData().toEntities()
        data.forEach{
            val sectorExtractName = it.sector.sectorName
            if (sectors.filter { sector -> sector.sectorName == sectorExtractName  }.isEmpty()){
                repositorySector.saveAndFlush(Sector(sectorName = sectorExtractName))
            }
        }
    }

    //TODO: change(create new method) importData and getEmissionsData to save Sector first
    private fun getEmissionsData(): EmissionsTO {
        return emissions ?: objectMapper.readValue(
            javaClass.getResource("/data/greenhouse-gas-emissions-leipzig.json"), EmissionsTO::class.java
        ).apply { emissions = this }
        importData();
    }

    fun getSectors(): List<String> {
        return listOf("Verkehr")
    }

    fun getYears(): List<Long> {
        return listOf(2018, 2019)
    }
}