package de.l.oklab.klimawatch.web

import de.l.oklab.klimawatch.emissions.EmissionsService
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("emissions")
class EmissionsController @ConstructorBinding constructor(
    internal val emissionsService: EmissionsService
) {

    @GetMapping("sectors")
    fun getSectors() = emissionsService.getSectors()

    @GetMapping("years")
    fun getYears() = emissionsService.getYears()

    @GetMapping("greenhouse-gases")
    fun getGreenhouseGasEmissions(
        @RequestParam(required = false) sector: String,
        @RequestParam(required = false) year: Int?
    ) = emissionsService.getBySectorAndYear(year = year, sector = sector)
}