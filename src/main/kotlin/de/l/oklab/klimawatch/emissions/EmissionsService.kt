package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.databind.ObjectMapper
import de.l.oklab.klimawatch.emissions.to.EmissionsData
import de.l.oklab.klimawatch.emissions.to.EmissionsTO
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Service

@Service
class EmissionsService @ConstructorBinding constructor(internal val objectMapper: ObjectMapper) {

    var emissions: EmissionsTO? = null

    fun getAll(): EmissionsTO = getEmissionsData()

    fun getBySector(sector: String, year: Int?): List<EmissionsData> {
        val data = getEmissionsData()
        val filteredBySector = data.data.filter { it.sector == sector }
        return if (year != null) {
            filteredBySector.map {
                EmissionsData(
                    sector = it.sector,
                    data = it.data.filter { timedData -> timedData.year == year }
                )
            }
        } else filteredBySector
    }

    fun getByYear(year: Int, sector: String?): List<EmissionsData> {
        val data = getEmissionsData()
        val filteredByYear = data.data.map {
            EmissionsData(
                sector = it.sector,
                data = it.data.filter { timedData -> timedData.year == year }
            )
        }
        return sector?.let { filteredByYear.filter { it.sector == sector } } ?: filteredByYear
    }

    private fun getEmissionsData(): EmissionsTO {
        return emissions ?: objectMapper.readValue(
            javaClass.getResource("/data/greenhouse-gas-emissions-leipzig.json"), EmissionsTO::class.java
        ).apply { emissions = this }
    }
}