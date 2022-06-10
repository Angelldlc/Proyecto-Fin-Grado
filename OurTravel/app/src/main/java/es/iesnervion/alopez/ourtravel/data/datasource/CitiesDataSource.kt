package es.iesnervion.alopez.ourtravel.data.datasource

import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface CitiesDataSource {
    suspend fun getCities() : List<City>
}