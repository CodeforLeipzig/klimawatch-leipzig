package de.l.oklab.klimawatch.catalog.to

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.l.oklab.klimawatch.catalog.to.ValueHandler.singleValue
import jakarta.json.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class Distribution(
    var id: String? = null,
    var type: String? = null,
    var downloadURL: String? = null,
    var mediaType: String? = null,
    var title: String? = null,
): CatalogElement() {
    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = singleValue(value)
            "@type" -> this.type = singleValue(value)
            "http://www.w3.org/ns/dcat#downloadURL" -> this.downloadURL = singleValue(value)
            "http://www.w3.org/ns/dcat#mediaType" -> this.mediaType = singleValue(value)
            "http://purl.org/dc/terms/title" -> this.title = singleValue(value)
            else -> println("missed $key in Distribution")
        }
    }

    override fun getResolvedType() = Types.Distribution
}
