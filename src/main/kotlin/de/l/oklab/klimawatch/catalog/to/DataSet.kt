package de.l.oklab.klimawatch.catalog.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.l.oklab.klimawatch.catalog.to.ValueHandler.listValue
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import jakarta.json.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataSet(
    var id: String? = null,
    var type: String? = null,
    var accrualPeriodicity: String? = null,
    var language: String? = null,
    var license: String? = null,
    var modified: String? = null,
    var publisher: String? = null,
    var title: String? = null,
    var dataFromDate: String? = null,
    var dataSource: String? = null,
    var dataUntilDate: String? = null,
    var datasetComment: String? = null,
    var odpGroup: List<String>? = null,
    var contactPoint: String? = null,
    var theme: String? = null,
    var distribution: List<String>? = null,
): CatalogElement() {

    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = value.toString()
            "@type" -> this.type = value.toString()
            "http://www.w3.org/ns/dcat#distribution" -> this.distribution = listValue(value)
            "http://statistik.leipzig.de/ontology/odpGroup" -> this.odpGroup = listValue(value)
            "http://purl.org/dc/terms/accrualPeriodicity" -> this.accrualPeriodicity = singleValue(value)
            "http://www.w3.org/ns/dcat#contactPoint" -> this.contactPoint = singleValue(value)
            "http://statistik.leipzig.de/ontology/dataSource" -> this.dataSource = singleValue(value)
            "http://statistik.leipzig.de/ontology/datasetComment" -> this.datasetComment = singleValue(value)
            "http://purl.org/dc/terms/language" -> this.language = singleValue(value)
            "http://purl.org/dc/terms/license" -> this.license = singleValue(value)
            "http://purl.org/dc/terms/modified" -> this.modified = singleValue(value)
            "http://purl.org/dc/terms/publisher" -> this.publisher = singleValue(value)
            "http://www.w3.org/ns/dcat#theme" -> this.theme = singleValue(value)
            "http://purl.org/dc/terms/title" -> this.title = singleValue(value)
            else -> println("missed $key")
        }
    }

    override fun getResolvedType() = Types.Dataset

    fun getResolvedAccrualPeriodicity(): AccrualPeriodicity? =
        this.accrualPeriodicity?.let { AccrualPeriodicity.from(it) }

    fun getResolvedLanguage() = this.language?.let { Language.from(it) }

    fun getResolvedPublisher(objectRegistry: ObjectRegistry) = this.publisher?.let { objectRegistry.organizations[it] }

    fun getResolvedDataSource(objectRegistry: ObjectRegistry) = this.dataSource?.let { objectRegistry.organizations[it] }

    fun getResolvedContactPoint(objectRegistry: ObjectRegistry) = this.contactPoint?.let { objectRegistry.organizations[it] }

    fun getResolvedTheme(objectRegistry: ObjectRegistry) = this.theme?.let { objectRegistry.concepts[it] }

    fun getResolvedDistributions(objectRegistry: ObjectRegistry) = this.distribution?.mapNotNull { objectRegistry.distributions[it] } ?: emptyList()
}
