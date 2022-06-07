package es.iesnervion.alopez.ourtravel.usecases.destinationlist

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.model.City
import es.iesnervion.alopez.ourtravel.domain.repository.DestinationRepository
import java.util.*

class AddDestination(
    private val destinationRepo: DestinationRepository
) {
    suspend operator fun invoke(
        id: String,
        city: City,
        description: String,
        accomodationCosts: Double,
        transportationCosts: Double,
        foodCosts: Double,
        tourismCosts: Double,
        startDate: Date,
        endDate: Date,
        travelStay: String,
        tourismAttractions: List<String>
    ) = destinationRepo.addDestinationToFirestore(id, city, description, accomodationCosts, transportationCosts, foodCosts, tourismCosts, startDate, endDate, travelStay, tourismAttractions)
}