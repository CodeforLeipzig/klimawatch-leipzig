package de.l.oklab.klimawatch.catalog.to

import jakarta.json.JsonString
import jakarta.json.JsonValue

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
        when (jsonValue?.valueType) {
            JsonValue.ValueType.OBJECT -> {
                jsonValue.asJsonObject()?.let {
                    if (it.isEmpty()) emptyList() else listOf(it.toString())
                }
            }

            JsonValue.ValueType.STRING -> {
                listOf((jsonValue as JsonString).string)
            }

            else -> {
                listOf(jsonValue.toString())
            }
        }
    }

    fun singleValue(jsonValue: JsonValue?) = listValue(jsonValue)?.let { if (it.isNotEmpty()) it[0] else null }
}
