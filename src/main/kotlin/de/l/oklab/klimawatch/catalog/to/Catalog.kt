package de.l.oklab.klimawatch.catalog.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.l.oklab.klimawatch.catalog.to.ValueHandler.listValue
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import jakarta.json.JsonValue


@JsonIgnoreProperties(ignoreUnknown = true)
data class Catalog(
    var id: String? = null,
    var type: String? = null,
    var title: String? = null,
    var decription: String? = null,
    var issued: String? = null,
    var modified: String? = null,
    var language: String? = null,
    var license: String? = null,
    var rights: String? = null,
    var spatial: String? = null,
    var homepage: String? = null,
    var publisher: String? = null,
    var label: String? = null,
    var dataset: List<String>? = null,
    var themeTaxonomy: String? = null,
): CatalogElement() {
    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = singleValue(value)
            "@type" -> this.type = singleValue(value)
            "http://www.w3.org/ns/dcat#dataset" -> this.dataset = listValue(value)
            "http://www.w3.org/2000/01/rdf-schema#label" -> this.label = singleValue(value)
            "http://purl.org/dc/terms/language" -> this.language = singleValue(value)
            "http://purl.org/dc/terms/license" -> this.license = singleValue(value)
            "http://purl.org/dc/terms/publisher" -> this.publisher = singleValue(value)
            "http://purl.org/dc/terms/title" -> this.title = singleValue(value)
            "http://www.w3.org/ns/dcat#themeTaxonomy" -> this.themeTaxonomy = singleValue(value)
            "http://xmlns.com/foaf/0.1/homepage" -> this.homepage = singleValue(value)
            else -> println("missed $key in Catalog")
        }
    }

    override fun getResolvedType() = Types.Catalog

    fun getResolvedPublisher(objectRegistry: ObjectRegistry) = this.publisher?.let { objectRegistry.organizations[it] }

    fun getResolvedDatasets(objectRegistry: ObjectRegistry) =
        this.dataset?.mapNotNull { objectRegistry.datasets[it] } ?: emptyList()

    fun getResolvedThemeTaxonomy(objectRegistry: ObjectRegistry) =
        this.themeTaxonomy?.let { objectRegistry.conceptSchemes[it] }
}
