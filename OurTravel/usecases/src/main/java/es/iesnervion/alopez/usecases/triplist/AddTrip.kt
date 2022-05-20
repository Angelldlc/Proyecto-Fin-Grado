package es.iesnervion.alopez.usecases.triplist

import es.iesnervion.alopez.domain.repository.TripRepository
import java.util.*

class AddTrip(
    private val repo: TripRepository
) {
    suspend operator fun invoke(
        id: String,
        name: String,
        startDate: Date,
        endDate: Date,
        totalCost: Double
    ) = repo.addTripToFirestore(id, name, startDate, endDate, totalCost)
}