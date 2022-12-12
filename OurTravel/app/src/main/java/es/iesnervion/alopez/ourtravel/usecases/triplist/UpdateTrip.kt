package es.iesnervion.alopez.ourtravel.usecases.triplist

import com.google.firebase.Timestamp
import es.iesnervion.alopez.ourtravel.domain.repository.TripRepository

class UpdateTrip(
    private val repo: TripRepository
) {
    suspend operator fun invoke(
        id: String,
        startDate: Timestamp,
        endDate: Timestamp,
        totalCost: Long
    ) = repo.updateTripFromFirestore(id, startDate, endDate, totalCost)
}