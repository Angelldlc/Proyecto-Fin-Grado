package es.iesnervion.alopez.domain.repository

import es.iesnervion.alopez.domain.model.Response
import kotlinx.coroutines.flow.Flow
import es.iesnervion.alopez.domain.model.TripPlanning
import java.util.*

interface TripRepository {
    fun getTripsFromFirestore(): Flow<Response<List<TripPlanning>>>

    suspend fun addTripToFirestore(id: String, name: String, startDate: Date, endDate: Date, totalCost: Double): Flow<Response<Void?>>

    suspend fun deleteTripFromFirestore(id: String): Flow<Response<Void?>>
}