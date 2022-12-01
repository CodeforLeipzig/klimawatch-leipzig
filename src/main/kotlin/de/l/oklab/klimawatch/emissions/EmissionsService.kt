package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.databind.ObjectMapper
import de.l.oklab.klimawatch.emissions.to.EmissionsData
import de.l.oklab.klimawatch.emissions.to.EmissionsTO
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Service

@Service
class EmissionsService @ConstructorBinding constructor(
        internal val objectMapper: ObjectMapper,
        internal val repository: EmissionsRepository,
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
        val data = getEmissionsData()
        // TODO extract all distinct sector names

        // TODO create sector entities and save them
        // TODO use saved sector entities in emissions (match by name)
        val entities = getEmissionsData().toEntities(/*sectors*/)
        //repository.saveAll(entities)
    }

    private fun getEmissionsData(): EmissionsTO {
        return emissions ?: objectMapper.readValue(
            javaClass.getResource("/data/greenhouse-gas-emissions-leipzig.json"), EmissionsTO::class.java
        ).apply { emissions = this }
    }

    fun getSectors(): List<String> {
        return listOf("Verkehr")
    }

    fun getYears(): List<Long> {
        return listOf(2018, 2019)
    }
}