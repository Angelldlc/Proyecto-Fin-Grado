package es.iesnervion.alopez.ourtravel.usecases.destinationlist

import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository
import java.util.*

class UpdateDestination(
    private val destinationRepo: DestinationRepository
) {
    suspend operator fun invoke(
        tripId: String,
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
        tourismAttractions: List<String>
    ) = destinationRepo.updateDestinationFromFirestore(
        tripId,
        id,
        city,
        description,
        accomodationCosts,
        transportationCosts,
        foodCosts,
        tourismCosts,
        startDate,
        endDate,
        travelStay,
        tourismAttractions
    )
}