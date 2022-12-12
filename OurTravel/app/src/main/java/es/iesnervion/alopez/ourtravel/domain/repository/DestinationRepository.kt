package es.iesnervion.alopez.ourtravel.domain.repository

import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.model.Destination
import es.iesnervion.alopez.ourtravel.domain.model.Response
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Interfaz DestinationRepository.
 *
 */
interface DestinationRepository {
    fun getDestinationsFromFirestore(tripId: String): Flow<Response<List<Destination>>>

    suspend fun addDestinationToFirestore(tripId: String,
                                          id: String,
                                          city: City,
                                          description: String,
                                          accomodationCosts: Long,
                                          transportationCosts: Long,
                                          foodCosts: Long,
                                          tourismCosts: Long,
                                          startDate: Date,
                                          endDate: Date,
                                          travelStay: String,
                                          tourismAttractions: List<String>): Flow<Response<Boolean>>

    suspend fun updateDestinationFromFirestore(tripId: String,
                                                id: String,
                                                city: City,
                                                description: String,
                                                accomodationCosts: Long,
                                                transportationCosts: Long,
                                                foodCosts: Long,
                                                tourismCosts: Long,
                                                startDate: Date,
                                                endDate: Date,
                                                travelStay: String,
                                                tourismAttractions: List<String>): Flow<Response<Boolean>>

    suspend fun deleteDestinationFromFirestore(tripId: String, id: String): Flow<Response<Boolean>>
}