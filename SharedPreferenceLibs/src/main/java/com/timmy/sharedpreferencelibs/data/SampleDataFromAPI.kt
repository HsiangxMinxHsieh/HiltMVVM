package com.timmy.sharedpreferencelibs.data

import com.google.gson.annotations.SerializedName
data class SampleDataFromAPI(
    @SerializedName("__extras")
    val extras: Extras = Extras(),
    @SerializedName("fields")
    val fields: List<Field> = listOf(),
    @SerializedName("include_total")
    val includeTotal: Boolean = false,
    @SerializedName("limit")
    val limit: String = "",
    @SerializedName("records")
    val records: List<Record> = listOf(),
    @SerializedName("resource_format")
    val resourceFormat: String = "",
    @SerializedName("resource_id")
    val resourceId: String = "",
    @SerializedName("total")
    val total: String = ""
)

data class Extras(
    @SerializedName("api_key")
    val apiKey: String = ""
)

data class Field(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("info")
    val info: Info = Info(),
    @SerializedName("type")
    val type: String = ""
)

data class Record(
    @SerializedName("item1")
    val item1: String = "",
    @SerializedName("item2")
    val item2: String = "",
    @SerializedName("value1")
    val value1: String = "",
    @SerializedName("value2")
    val value2: String = "",
    @SerializedName("value3")
    val value3: String = "",
    @SerializedName("value4")
    val value4: String = "",
    @SerializedName("value5")
    val value5: String = "",
    @SerializedName("value6")
    val value6: String = "",
    @SerializedName("value7")
    val value7: String = ""
)

data class Info(
    @SerializedName("label")
    val label: String = ""
)
