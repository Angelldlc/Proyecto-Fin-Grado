package es.iesnervion.alopez.ourtravel.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityListDTO (
    @Json(name = "cities") val cities: List<CityDTO>
)
