package es.iesnervion.alopez.ourtravel.data.network

import es.iesnervion.alopez.ourtravel.data.network.model.CityDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("cities/")
    suspend fun getCities(): Response<List<CityDTO>>
}