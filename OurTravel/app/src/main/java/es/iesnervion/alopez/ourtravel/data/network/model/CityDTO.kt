package es.iesnervion.alopez.ourtravel.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data Class pública CityDTO.
 *
 * Clase de datos pública que representa una ciudad obtenida de una API.
 *
 */
@JsonClass(generateAdapter = true)
data class CityDTO(
    @Json(name = "name") val name: String?,
    @Json(name = "photo") val photo: String?,

)