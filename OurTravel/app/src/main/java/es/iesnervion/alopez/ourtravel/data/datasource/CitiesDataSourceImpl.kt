package es.iesnervion.alopez.ourtravel.data.datasource

import es.iesnervion.alopez.ourtravel.data.network.ApiService
import es.iesnervion.alopez.ourtravel.data.network.mapper.toDomain
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CitiesDataSourceImpl @Inject constructor(private val retrofit: Retrofit): CitiesDataSource {

    override suspend fun getCities(): List<City> {
        val networkResponse = retrofit.create(ApiService::class.java).getCities()

        val networkList =
            if (networkResponse.isSuccessful) networkResponse.body() else emptyList()

        return networkList?.map { it.toDomain() } ?: emptyList()
    }
}