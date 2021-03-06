package com.waz.zclient.core.utilities.converters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class JsonConverter<T>(private val serializer: KSerializer<T>) {
    private val json by lazy { Json(JsonConfiguration.Stable.copy(isLenient = true, ignoreUnknownKeys = true)) }

    fun fromJson(jsonString: String): T = json.parse(serializer, jsonString)

    fun toJson(model: T): String = json.stringify(serializer, model)

    fun fromJsonList(jsonString: String): List<T> = json.parse(serializer.list, jsonString)

    fun toJsonList(models: List<T>): String = json.stringify(serializer.list, models)
}
