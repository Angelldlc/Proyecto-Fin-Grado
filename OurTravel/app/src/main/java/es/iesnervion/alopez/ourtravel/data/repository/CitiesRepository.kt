package es.iesnervion.alopez.ourtravel.data.repository

import es.iesnervion.alopez.ourtravel.data.datasource.CitiesDataSource
import es.iesnervion.alopez.ourtravel.domain.model.City
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Clase pública CitiesRepository.
 *
 */
@Singleton
class CitiesRepository @Inject constructor(private val citiesDataSource: CitiesDataSource) {
    suspend fun getCities(): List<City> = citiesDataSource.getCities()
}