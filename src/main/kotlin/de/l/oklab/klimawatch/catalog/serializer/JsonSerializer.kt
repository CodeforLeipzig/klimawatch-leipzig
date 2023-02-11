package de.l.oklab.klimawatch.catalog.serializer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.l.oklab.klimawatch.catalog.to.ObjectRegistry

class JsonSerializer {

    fun serialize(objectRegistry: ObjectRegistry) {
        val objectMapper = jacksonObjectMapper()
        println(objectRegistry.conceptSchemes.values.sortedBy { it.prefLabel }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(objectRegistry.concepts.values.sortedBy { it.prefLabel }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(objectRegistry.datasets.values.sortedBy { it.title }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(objectRegistry.distributions.values.sortedBy { it.title }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println(objectRegistry.organizations.values.sortedBy { it.label }
            .map { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(it) })
        println("*******")
        println(objectRegistry.datasets.values.sortedBy { it.title }.map { it.title })
    }
}