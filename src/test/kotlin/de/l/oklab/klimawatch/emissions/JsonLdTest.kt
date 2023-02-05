package de.l.oklab.klimawatch.emissions

import com.apicatalog.jsonld.JsonLd
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.l.oklab.klimawatch.emissions.ValueHandler.listValue
import de.l.oklab.klimawatch.emissions.ValueHandler.singleValue
import jakarta.json.JsonString
import jakarta.json.JsonValue
import org.junit.jupiter.api.Test


object ValueHandler {
    fun listValue(jsonValue: JsonValue?): List<String>? = if (jsonValue?.valueType == JsonValue.ValueType.ARRAY) {
        jsonValue.asJsonArray()?.let {
            if (it.isEmpty()) null else {
                it.mapIndexed { index, value ->
                    when (value.valueType) {
                        JsonValue.ValueType.STRING -> {
                            it.getJsonString(index).string
                        }

                        JsonValue.ValueType.OBJECT -> {
                            value.asJsonObject().keys.joinToString(", ") { key ->
                                value.asJsonObject().getJsonString(key).string
                            }
                        }

                        JsonValue.ValueType.ARRAY -> {
                            value.asJsonArray().toString()
                        }

                        else -> {
                            value.toString()
                        }
                    }
                }
            }
        } ?: emptyList()
    } else {
        if (jsonValue?.valueType == JsonValue.ValueType.OBJECT) {
            jsonValue.asJsonObject()?.let {
                if (it.isEmpty()) emptyList() else listOf(it.toString())
            }
        } else if (jsonValue?.valueType == JsonValue.ValueType.STRING) {
            listOf((jsonValue as JsonString).string)
        } else {
            listOf(jsonValue.toString())
        }
    }

    fun singleValue(jsonValue: JsonValue?) = listValue(jsonValue)?.let { if (it.isNotEmpty()) it[0] else null }
}

enum class Types(val namespace: String) {
    Concept("http://www.w3.org/2004/02/skos/core#Concept"),
    ConceptScheme("http://www.w3.org/2004/02/skos/core#ConceptScheme"),
    Catalog("http://www.w3.org/ns/dcat#Catalog"),
    Dataset("http://www.w3.org/ns/dcat#Dataset"),
    Distribution("http://www.w3.org/ns/dcat#Distribution"),
    Organization("http://xmlns.com/foaf/0.1/Organization");

    companion object {
        fun from(key: String): Types? = values().firstOrNull { it.namespace == key }
    }
}

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
    var hompage: String? = null,
    var publisher: String? = null,
    var label: String? = null,
    var dataset: List<String>? = null,
    var themeTaxonomie: String? = null,
) {
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
            else -> println("missed $key in Catalog")
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Concept(
    var id: String? = null,
    var type: String? = null,
    var hasTopConcept: String? = null,
    var prefLabel: String? = null,
    var inScheme: String? = null,
    var narrower: String? = null,
    var topConceptOf: String? = null,
    var broader: String? = null,

    ) {
    fun setter(key: String, value: JsonValue?) {
        when (key) {
            "@id" -> this.id = singleValue(value)
            "@type" -> this.type = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#hasTopConcept" -> this.hasTopConcept = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#prefLabel" -> this.prefLabel = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#inScheme" -> this.inScheme = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#narrower" -> this.narrower = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#topConceptOf" -> this.topConceptOf = singleValue(value)
            "http://www.w3.org/2004/02/skos/core#broader" -> this.broader = singleValue(value)
            else -> println("missed $key in Concept")
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ConceptScheme(
    var id: String? = null,
    var type: String? = null,
    var inScheme: String? = null,
    var narrower: String? = null,
    var prefLabel: String? = null,
    var hasTopConcept: String? = null,
    var topConceptOf: String? = null
) {
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
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Distribution(
    var id: String? = null,
    var type: String? = null,
    var downloadURL: String? = null,
    var mediaType: String? = null,
    var title: String? = null,
) {
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
}

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
) {
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
}


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
) {

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
}

class JsonLdTest {

    @Test
    fun readJsonLd() {
        val objectMapper = jacksonObjectMapper()
        val catalog = JsonLd.expand("file:///home/joerg/Schreibtisch/statistik-katalog.json")
            .ordered()
            .get()
        val iter = catalog.iterator()
        val concepts = mutableSetOf<Concept>()
        val conceptSchemes = mutableSetOf<ConceptScheme>()
        val cat = mutableSetOf<Catalog>()
        val distributions = mutableSetOf<Distribution>()
        val organizations = mutableSetOf<Organization>()

        val datasets = mutableSetOf<DataSet>()
        while (iter.hasNext()) {
            val next = iter.next().asJsonObject()
            val key: String? = singleValue(next["@type"])
            val type = key?.let { Types.from(key) }
            when (type) {
                Types.Catalog -> {
                    val catalog = Catalog().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    cat.add(catalog)
                }

                Types.Concept -> {
                    val concept = Concept().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    concepts.add(concept)
                }

                Types.ConceptScheme -> {
                    val conceptScheme = ConceptScheme().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    conceptSchemes.add(conceptScheme)
                }

                Types.Dataset -> {
                    val dataSet = DataSet().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    datasets.add(dataSet)
                }

                Types.Distribution -> {
                    val distribution = Distribution().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    distributions.add(distribution)
                }

                Types.Organization -> {
                    val organization = Organization().apply {
                        next.keys.map { attKey -> setter(attKey, next[attKey]) }
                    }
                    organizations.add(organization)
                }

                else -> println("missed type $type")
            }
        }
        println(conceptSchemes.sortedBy { it.prefLabel }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(concepts.sortedBy { it.prefLabel }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(datasets.sortedBy { it.title }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(distributions.sortedBy { it.title }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(organizations.sortedBy { it.label }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
    }
}



