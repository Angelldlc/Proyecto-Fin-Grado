package es.iesnervion.alopez.ourtravel.data.datasource

import es.iesnervion.alopez.ourtravel.domain.model.City

/**
 * Interfaz CitiesDataSource.
 *
 */
interface CitiesDataSource {
    suspend fun getCities() : List<City>
}