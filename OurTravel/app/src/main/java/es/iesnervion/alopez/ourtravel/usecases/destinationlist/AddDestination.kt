package es.iesnervion.alopez.ourtravel.usecases.destinationlist

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository
import java.util.*

class AddDestination(
    private val destinationRepo: DestinationRepository
) {
    suspend operator fun invoke(
        tripId: String,
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
    ) = destinationRepo.addDestinationToFirestore(tripId, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions)
}