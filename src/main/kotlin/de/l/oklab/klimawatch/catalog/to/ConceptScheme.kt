package de.l.oklab.klimawatch.catalog.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import jakarta.json.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class ConceptScheme(
    var id: String? = null,
    var type: String? = null,
    var inScheme: String? = null,
    var narrower: String? = null,
    var prefLabel: String? = null,
    var hasTopConcept: String? = null,
    var topConceptOf: String? = null
): CatalogElement() {
    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = singleValue(value)
            "@type" -> this.type = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#hasTopConcept" -> this.hasTopConcept = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#inScheme" -> this.inScheme = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#narrower" -> this.narrower = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#prefLabel" -> this.prefLabel = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#topConceptOf" -> this.topConceptOf = singleValue(value)
            else -> println("missed $key in ConceptScheme")
        }
    }

    override fun getResolvedType() = Types.ConceptScheme

    fun getResolvedInScheme(objectRegistry: ObjectRegistry) = this.inScheme?.let { objectRegistry.conceptSchemes[it] }

    fun getResolvedNarrower(objectRegistry: ObjectRegistry) = this.narrower?.let { objectRegistry.concepts[it] }

    fun getResolvedHasTopConcept(objectRegistry: ObjectRegistry) = this.hasTopConcept?.let { objectRegistry.concepts[it] }

    fun getResolvedTopConceptOf(objectRegistry: ObjectRegistry) = this.topConceptOf?.let { objectRegistry.concepts[it] }
}
