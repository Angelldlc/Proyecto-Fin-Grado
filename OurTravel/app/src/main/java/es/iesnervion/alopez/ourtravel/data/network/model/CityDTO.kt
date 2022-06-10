package es.iesnervion.alopez.ourtravel.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDTO(
    @Json(name = "name") val name: String?,
    @Json(name = "photo") val photo: String?,

)