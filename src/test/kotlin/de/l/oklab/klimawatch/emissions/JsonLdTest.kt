package de.l.oklab.klimawatch.emissions

import com.apicatalog.jsonld.JsonLd
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.json.JsonValue
import org.junit.jupiter.api.Test

class JsonLdTest {

    // types:
    // "http://www.w3.org/2004/02/skos/core#Concept"
    // "http://www.w3.org/2004/02/skos/core#ConceptScheme"
    // "http://www.w3.org/ns/dcat#Catalog"
    // "http://www.w3.org/ns/dcat#Dataset"
    // "http://www.w3.org/ns/dcat#Distribution"
    // "http://xmlns.com/foaf/0.1/Organization"


    @Test
    fun readJsonLd() {
        val objectMapper = jacksonObjectMapper()
        val catalog = JsonLd.expand("file:///home/joerg/Schreibtisch/statistik-katalog.json")
            .ordered()
            .get()
        val iter = catalog.iterator()
        val types = mutableSetOf<String>()
        val datasets = mutableSetOf<DataSet>()
        while (iter.hasNext()) {
            val next = iter.next().asJsonObject()
            val key: String? = singleValue(next["@type"])
            if (key == "http://www.w3.org/ns/dcat#Dataset") {
                val dataSet = DataSet()
                dataSet.id = null
                dataSet.type = key
                for (entry in mappedSetter.entries) {
                    val value = next[entry.key]
                    entry.value(dataSet, value)
                }
                datasets.add(dataSet)
            }
            types.add(key.toString())
        }
        //println(types.sorted())
        println(datasets.sortedBy { it.title }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
    }

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
        } else {
            listOf(jsonValue.toString())
        }
    }

    fun singleValue(jsonValue: JsonValue?) = listValue(jsonValue)?.let { if (it.isNotEmpty()) it[0] else null }


    val mappedSetter: MutableMap<String, (DataSet, JsonValue?) -> Unit> = mutableMapOf(
        "http://purl.org/dc/terms/accrualPeriodicity" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.accrualPeriodicity = singleValue(value)
        },
        "http://www.w3.org/ns/dcat#contactPoint" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.contactPoint = singleValue(value)
        },
        "http://statistik.leipzig.de/ontology/dataSource" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.dataSource = singleValue(value)
        },
        "http://statistik.leipzig.de/ontology/datasetComment" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.datasetComment = singleValue(value)
        },
        "http://www.w3.org/ns/dcat#distribution" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.distribution = listValue(value)
        },
        "http://purl.org/dc/terms/language" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.language = singleValue(value)
        },
        "http://purl.org/dc/terms/license" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.license = singleValue(value)
        },
        "http://purl.org/dc/terms/modified" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.modified = singleValue(value)
        },
        "http://statistik.leipzig.de/ontology/odpGroup" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.odpGroup = listValue(value)
        },
        "http://purl.org/dc/terms/publisher" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.publisher = singleValue(value)
        },
        "http://www.w3.org/ns/dcat#theme" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.theme = singleValue(value)
        },
        "http://purl.org/dc/terms/title" to { dataSet: DataSet, value: JsonValue? ->
            dataSet.title = singleValue(value)
        },
    )
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
)
