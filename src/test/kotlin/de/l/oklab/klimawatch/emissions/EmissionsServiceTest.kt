package de.l.oklab.klimawatch.emissions

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EmissionsServiceTest {

    @Test
    fun testGetBySectorAndYear() {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val emissionsService = EmissionsService(objectMapper)
        val result = emissionsService.getBySectorAndYear("Verkehr", 2016)
        val writer = objectMapper.writerWithDefaultPrettyPrinter()
        assertEquals("""
            [ {
              "Sektor" : "Verkehr",
              "Werte" : [ {
                "Jahr" : 2016,
                "Wert" : 1.42
              } ]
            } ]
        """.trimIndent(), writer.writeValueAsString(result))
    }

    @Test
    fun testGetBySectorOnly() {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val emissionsService = EmissionsService(objectMapper)
        val result = emissionsService.getBySectorAndYear("Verkehr", null)
        val writer = objectMapper.writerWithDefaultPrettyPrinter()
        assertEquals("""
            [ {
              "Sektor" : "Verkehr",
              "Werte" : [ {
                "Jahr" : 2011,
                "Wert" : 1.46
              }, {
                "Jahr" : 2012,
                "Wert" : 1.44
              }, {
                "Jahr" : 2013,
                "Wert" : 1.43
              }, {
                "Jahr" : 2014,
                "Wert" : 1.45
              }, {
                "Jahr" : 2015,
                "Wert" : 1.43
              }, {
                "Jahr" : 2016,
                "Wert" : 1.42
              }, {
                "Jahr" : 2017,
                "Wert" : 1.39
              }, {
                "Jahr" : 2018,
                "Wert" : 1.37
              }, {
                "Jahr" : 2019,
                "Wert" : 1.35
              } ]
            } ]
        """.trimIndent(), writer.writeValueAsString(result))
    }

    @Test
    fun testGetByNonExistingSectorOnly() {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val emissionsService = EmissionsService(objectMapper)
        val result = emissionsService.getBySectorAndYear("Gebäude", null)
        val writer = objectMapper.writerWithDefaultPrettyPrinter()
        assertEquals("""
            [ ]
        """.trimIndent(), writer.writeValueAsString(result))
    }

    @Test
    fun testGetByYearOnly() {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val emissionsService = EmissionsService(objectMapper)
        val result = emissionsService.getBySectorAndYear(null, 2016 )
        val writer = objectMapper.writerWithDefaultPrettyPrinter()
        assertEquals("""
            [ {
              "Sektor" : "Verkehr",
              "Werte" : [ {
                "Jahr" : 2016,
                "Wert" : 1.42
              } ]
            }, {
              "Sektor" : "Private Haushalte",
              "Werte" : [ {
                "Jahr" : 2016,
                "Wert" : 1.81
              } ]
            }, {
              "Sektor" : "Wirtschaft und kommunale Einrichtungen",
              "Werte" : [ {
                "Jahr" : 2016,
                "Wert" : 2.77
              } ]
            } ]
        """.trimIndent(), writer.writeValueAsString(result))
    }

    @Test
    fun testGetByNonExistingYear() {
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val emissionsService = EmissionsService(objectMapper)
        val result = emissionsService.getBySectorAndYear(null, 2020 )
        val writer = objectMapper.writerWithDefaultPrettyPrinter()
        assertEquals("""
            [ ]
        """.trimIndent(), writer.writeValueAsString(result))
    }

}