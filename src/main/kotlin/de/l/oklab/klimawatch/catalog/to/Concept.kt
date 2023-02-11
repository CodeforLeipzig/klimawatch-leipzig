package de.l.oklab.klimawatch.catalog.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.l.oklab.klimawatch.catalog.to.ValueHandler.listValue
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import jakarta.json.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class Concept(
    var id: String? = null,
    var type: String? = null,
    var hasTopConcept: String? = null,
    var prefLabel: String? = null,
    var inScheme: String? = null,
    var narrower: List<String>? = null,
    var topConceptOf: String? = null,
    var broader: String? = null
) : CatalogElement() {
    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = singleValue(value)
            "@type" -> this.type = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#hasTopConcept" -> this.hasTopConcept = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#prefLabel" -> this.prefLabel = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#inScheme" -> this.inScheme = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#narrower" -> this.narrower = listValue(value)
            "http://www.w3.org/2004/02/skos/core#topConceptOf" -> this.topConceptOf = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#broader" -> this.broader = singleValue(value)
            else -> println("missed $key in Concept")
        }
    }

    override fun getResolvedType() = Types.Concept

    fun getResolvedHasTopConcept(objectRegistry: ObjectRegistry) =
        this.hasTopConcept?.let { objectRegistry.concepts[it] }

    fun getResolvedTopConceptOf(objectRegistry: ObjectRegistry) = this.topConceptOf?.let { objectRegistry.concepts[it] }

    fun getResolvedInScheme(objectRegistry: ObjectRegistry) = this.inScheme?.let { objectRegistry.conceptSchemes[it] }

    fun getResolvedNarrower(objectRegistry: ObjectRegistry) =
        this.narrower?.mapNotNull { objectRegistry.concepts[it] } ?: emptyList()

    fun getResolvedBroader(objectRegistry: ObjectRegistry) = this.broader?.let { objectRegistry.concepts[it] }

    fun getResolvedDataSets(objectRegistry: ObjectRegistry) =
        objectRegistry.datasets.values.filter { it.theme == this.id }.sortedBy { it.title }
}
