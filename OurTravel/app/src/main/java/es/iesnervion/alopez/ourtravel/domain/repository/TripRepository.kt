package es.iesnervion.alopez.ourtravel.domain.repository

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun getTripsFromFirestore(): Flow<Response<List<TripPlanning>>>

    fun getLastTripInsertedId(): Flow<Response<String>>

    suspend fun addTripToFirestore(id: String, name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String): Flow<Response<Boolean>>

    suspend fun deleteTripFromFirestore(id: String): Flow<Response<Boolean>>
}