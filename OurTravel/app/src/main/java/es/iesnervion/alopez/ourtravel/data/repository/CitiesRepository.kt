package es.iesnervion.alopez.ourtravel.data.repository

import es.iesnervion.alopez.ourtravel.data.datasource.CitiesDataSource
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesRepository @Inject constructor(private val citiesDataSource: CitiesDataSource) {
    suspend fun getCities(): List<City> = citiesDataSource.getCities()
}