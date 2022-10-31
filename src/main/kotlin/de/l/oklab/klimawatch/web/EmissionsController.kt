package de.l.oklab.klimawatch.web

import de.l.oklab.klimawatch.emissions.EmissionsService
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("emissions")
class EmissionsController @ConstructorBinding constructor(
    internal val emissionsService: EmissionsService
) {

    @GetMapping("greenhouse-gas")
    fun getGreenhouseGasEmissions() = emissionsService.getAll()

    @GetMapping("greenhouse-gases/bysector/{sector}")
    fun getGreenhouseGasEmissionsBySectorAndYear(
        @PathVariable("sector") sector: String,
        @RequestParam(required = false) year: Int?
    ) = emissionsService.getBySector(year = year, sector = sector)

    @GetMapping("greenhouse-gases/byyear/{year}")
    fun getGreenhouseGasEmissionsByYear(
        @PathVariable("year") year: Int,
        @RequestParam(required = false) sector: String?
    ) = emissionsService.getByYear(year = year, sector = sector)
}