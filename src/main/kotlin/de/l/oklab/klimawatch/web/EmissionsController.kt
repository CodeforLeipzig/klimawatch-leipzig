package de.l.oklab.klimawatch.web

import de.l.oklab.klimawatch.emissions.EmissionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("emissions")
class EmissionsController @ConstructorBinding constructor(
    internal val emissionsService: EmissionsService
) {

    @GetMapping("sectors")
    fun getSectors() = emissionsService.getSectors()

    @GetMapping("emissions-data")
    fun getEmissionsData() = emissionsService.importData()/*getEmissionsData()*/

    @GetMapping("years")
    fun getYears() = emissionsService.getYears()

    //TODO:Change Sector to Entity
    @GetMapping("greenhouse-gases")
    fun getGreenhouseGasEmissions(
        @RequestParam(required = false) sector: String,
        @RequestParam(required = false) year: Int?
    ) = emissionsService.getBySectorAndYear(year = year, sector = sector)
}