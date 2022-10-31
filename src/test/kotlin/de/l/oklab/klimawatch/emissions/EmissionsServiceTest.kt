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
        val result = emissionsService.getBySector("Verkehr", 2016)
        assertEquals(1, result.size)
        val entry = result[0]
        assertEquals("Verkehr", entry.sector)
        assertEquals(1, entry.data.size)
        val value = entry.data[0]
        assertEquals(2016, value.year)
        assertEquals(1.42, value.value)
    }
}