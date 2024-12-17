package es.iesnervion.alopez.ourtravel.domain.repository

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz TripRepository.
 *
 */
typealias TripPlannings = List<TripPlanning>
typealias TripPlanningResponse = Response<TripPlannings>
typealias AddTripPlanningResponse = Response<Boolean>
typealias UpdateTripPlanningResponse = Response<Boolean>
typealias DeleteTripPlanningResponse = Response<Boolean>

interface TripRepository {
    fun getTripsFromFirestore(): Flow<TripPlanningResponse>

    suspend fun getTripFromFirestore(id: String): TripPlanning?

    suspend fun getLastTripInsertedId(): String?

    suspend fun addTripToFirestore(name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String, creationDate: Timestamp): AddTripPlanningResponse

    suspend fun updateTripFromFirestore(id: String, name: String, startDate: Timestamp, endDate: Timestamp, totalCost: Long, photo: String?): UpdateTripPlanningResponse

    suspend fun deleteTripFromFirestore(id: String): DeleteTripPlanningResponse
}