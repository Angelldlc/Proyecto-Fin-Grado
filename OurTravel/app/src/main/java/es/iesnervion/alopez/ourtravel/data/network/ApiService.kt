package es.iesnervion.alopez.ourtravel.data.network

import es.iesnervion.alopez.ourtravel.data.network.model.CityDTO
import es.iesnervion.alopez.ourtravel.data.network.model.CityListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("cities/")
    suspend fun getCities(): Response<List<CityDTO>>
}