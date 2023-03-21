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

typealias Destinations = List<Destination>
typealias DestinationsResponse = Response<Destinations>
typealias AddDestinationResponse = Response<Boolean>
typealias UpdateDestinationResponse = Response<Boolean>
typealias DeleteDestinationResponse = Response<Boolean>

interface DestinationRepository {
    fun getDestinationsFromFirestore(tripId: String): Flow<DestinationsResponse>

    suspend fun getLastDestinationInsertedId(tripId: String): String?
    suspend fun addDestinationToFirestore(tripId: String,
                                          city: City,
                                          description: String,
                                          accomodationCosts: Long,
                                          transportationCosts: Long,
                                          foodCosts: Long,
                                          tourismCosts: Long,
                                          startDate: Date,
                                          endDate: Date,
                                          travelStay: String,
                                          tourismAttractions: List<String>,
                                          creationDate: Date): AddDestinationResponse

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
                                                tourismAttractions: List<String>): UpdateDestinationResponse

    suspend fun deleteDestinationFromFirestore(tripId: String, id: String): DeleteDestinationResponse

}