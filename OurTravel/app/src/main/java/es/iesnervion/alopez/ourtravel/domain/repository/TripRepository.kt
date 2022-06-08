package es.iesnervion.alopez.ourtravel.domain.repository

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.Response
import kotlinx.coroutines.flow.Flow
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import java.util.*

interface TripRepository {
    fun getTripsFromFirestore(): Flow<Response<List<TripPlanning>>>

    suspend fun addTripToFirestore(id: String,
                                   name: String,
                                   startDate: Timestamp,
                                   endDate: Timestamp,
                                   totalAccomodationCosts: Long,
                                   totalTransportationCosts: Long,
                                   totalFoodCosts: Long,
                                   totalTourismCosts: Long): Flow<Response<Boolean>>

    suspend fun deleteTripFromFirestore(id: String): Flow<Response<Void?>>
}