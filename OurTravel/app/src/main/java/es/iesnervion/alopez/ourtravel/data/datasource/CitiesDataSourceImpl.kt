package es.iesnervion.alopez.ourtravel.data.datasource

import es.iesnervion.alopez.ourtravel.data.network.ApiService
import es.iesnervion.alopez.ourtravel.data.network.mapper.toDomain
import es.iesnervion.alopez.ourtravel.domain.model.City
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Clase pública CitiesDataSourceImpl.
 *
 * Clase pública que implementa la interfaz CitiesDataSource, esta clase se encarga de realizar
 * llamadas a una API para recoger una lista de City.
 *
 */
@Singleton
class CitiesDataSourceImpl @Inject constructor(private val retrofit: Retrofit): CitiesDataSource {

    /**
     * Método público implementado asíncrono getCities.
     *
     * Método público para recoger una lista de City. El método utiliza una instancia de Retrofit
     * para realizar la llamada API, en caso de que la llamada se realice exitosamente devolverá
     * una lista con las City obtenidas, por el contrario la lista estará vacía.
     *
     * Entradas: void
     * Salidas: List<City>
     *
     * ******************************************************************************************
     * ******************************************************************************************
     * Comentario: Por alguna razón que desconozco, algunas veces las llamadas se realizan
     * correctamente y el código que devuelve es 200 OK y otras veces 500 Internal Server Error.
     *
     */
    override suspend fun getCities(): List<City> {
        val retrofitAPI = retrofit.create(ApiService::class.java)
        val networkResponse = retrofitAPI.getCities()
        delay(1500)
        val networkList =
            if (networkResponse.isSuccessful) networkResponse.body() else emptyList()

        return networkList?.map { it.toDomain() } ?: emptyList()
    }
}