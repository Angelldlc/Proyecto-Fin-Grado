package es.iesnervion.alopez.ourtravel.data.network

import es.iesnervion.alopez.ourtravel.data.network.model.CityDTO
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz ApiService.
 *
 */
interface ApiService {

    /**
     * Método público asíncrono getCities.
     *
     * Método que realiza una llamada GET a una API con la ruta establecida.
     *
     */
    @GET("cities/")
    suspend fun getCities(): Response<List<CityDTO>>
}