package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

class ReadTest {

    @Test
    fun test() {
        val vehicleCount = this.javaClass.getResource("/data/vehicles-count.json")
        val objectMapper = jacksonObjectMapper().registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )
        val typeRef: TypeReference<List<Vehicle>> = object: TypeReference<List<Vehicle>>() {}
        val node = objectMapper.readValue(vehicleCount, typeRef)
        println(node)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Vehicle(
    val name: String,
    val wert: Float,
    val jahr: Int,
)