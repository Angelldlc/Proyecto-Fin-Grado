package es.iesnervion.alopez.ourtravel.domain.repository

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import es.iesnervion.alopez.ourtravel.domain.model.TripPlanning
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DestinationRepository {
    fun getDestinationsFromFirestore(tripId: String): Flow<Response<List<Destination>>>

    suspend fun addDestinationToFirestore(id: String,
                                          city: City,
                                          description: String,
                                          accomodationCosts: Double,
                                          transportationCosts: Double,
                                          foodCosts: Double,
                                          tourismCosts: Double,
                                          startDate: Date,
                                          endDate: Date,
                                          travelStay: String,
                                          tourismAttractions: List<String>): Flow<Response<Boolean>>

    suspend fun deleteDestinationFromFirestore(id: String): Flow<Response<Void?>>
}