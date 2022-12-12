package es.iesnervion.alopez.ourtravel.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data Class pública CityListDTO.
 *
 * Clase de datos pública que representa una lista de ciudades obtenida de una API.
 *
 */
@JsonClass(generateAdapter = true)
data class CityListDTO (
    @Json(name = "cities") val cities: List<CityDTO>
)
