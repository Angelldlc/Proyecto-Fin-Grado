package es.iesnervion.alopez.ourtravel.domain.repository

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz TripRepository.
 *
 */
interface TripRepository {
    fun getTripsFromFirestore(): Flow<Response<List<TripPlanning>>>

    /*fun getLastTripInsertedId(): Flow<Response<String>>*/

    suspend fun addTripToFirestore(name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String, creationDate: Timestamp): Flow<Response<Boolean>>

    suspend fun updateTripFromFirestore(id: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long): Flow<Response<Boolean>>

    suspend fun deleteTripFromFirestore(id: String): Flow<Response<Boolean>>
}