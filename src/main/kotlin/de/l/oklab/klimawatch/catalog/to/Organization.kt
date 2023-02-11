package de.l.oklab.klimawatch.catalog.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import jakarta.json.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class Organization(
    var id: String? = null,
    var type: String? = null,
    var email: String? = null,
    var fax: String? = null,
    var label: String? = null,
    var locality: String? = null,
    var tel: String? = null,
    var url: String? = null
): CatalogElement() {
    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = singleValue(value)
            "@type" -> this.type = singleValue(value)
            "http://www.w3.org/2006/vcard/ns#email" -> this.email = singleValue(value)
            "http://statistik.leipzig.de/ontology/fax" -> this.fax = singleValue(value)
            "http://www.w3.org/2000/01/rdf-schema#label" -> this.label = singleValue(value)
            "http://www.w3.org/2006/vcard/ns#locality" -> this.locality = singleValue(value)
            "http://www.w3.org/2006/vcard/ns#tel" -> this.tel = singleValue(value)
            "http://www.w3.org/2006/vcard/ns#url" -> this.url = singleValue(value)
            else -> println("missed $key in Organization")
        }
    }

    override fun getResolvedType() = Types.Organization
}
